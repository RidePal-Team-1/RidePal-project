package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Track;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.services.contracts.BingMapService;
import com.example.ridepal.services.contracts.PixabayService;
import com.example.ridepal.services.contracts.PlaylistService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.*;

import static com.example.ridepal.filters.specifications.PlaylistSpecifications.*;


@Service
public class PlaylistServiceImpl implements PlaylistService {

    public static final int BUFFER_TRACKS = 12;
    public static final String UNAUTHORIZED_MSG = "You are not authorized to perform this operation!";
    private final PlaylistRepository playlistRepository;

    private final BingMapService bingMapService;

    private final PlaylistMapper playlistMapper;

    private final TrackRepository trackRepository;

    private final PixabayService pixabayService;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository,
                               BingMapService bingMapService,
                               PlaylistMapper playlistMapper,
                               TrackRepository trackRepository, PixabayService pixabayService) {
        this.playlistRepository = playlistRepository;
        this.bingMapService = bingMapService;
        this.playlistMapper = playlistMapper;
        this.trackRepository = trackRepository;
        this.pixabayService = pixabayService;
    }

    @Override
    public Page<Playlist> findAll(String title, String genre, String minDuration, String maxDuration, Pageable pageable){
            Specification<Playlist> filters = Specification.where
                            (StringUtils.isEmptyOrWhitespace(title) ? null : title(title))
                    .and(StringUtils.isEmptyOrWhitespace(genre) ? null : genre(genre))
                    .and(StringUtils.isEmptyOrWhitespace(minDuration) ? null : minDuration(minDuration))
                    .and(StringUtils.isEmptyOrWhitespace(maxDuration) ? null : maxDuration(maxDuration));
            return playlistRepository.findAll(filters, pageable);
    }

    @Override
    public Playlist getPlaylistById(int id) {
        if(playlistRepository.findById(id)==null){
            throw new EntityNotFoundException("Playlist", id);
        }else
            return playlistRepository.findById(id);
    }
    @Override
    public Playlist createPlaylist(PlaylistDto dto, User user) {
        if (dto.getGenres() == null) {
            //If there is no genre added
            dto.setGenres(new HashMap<>());
            dto.getGenres().put("Rap/Hip Hop", 16.66);
            dto.getGenres().put("Rock", 16.66);
            dto.getGenres().put("Pop", 16.66);
            dto.getGenres().put("Jazz", 16.66);
            dto.getGenres().put("Electro", 16.66);
            dto.getGenres().put("Dance", 16.66);
        } else {
            int unPopulatedFields = 0;
            double totalPercentages = 0;
            for (Map.Entry<String, Double> genre : dto.getGenres().entrySet()) {
                if (genre.getValue() == 0.0) {
                    unPopulatedFields++;
                } else {
                    totalPercentages += genre.getValue();
                }
            }
            // We make front end check to see if total percentages is below 100.
            if (unPopulatedFields > 0) {
                double percentageLeft = (100 - totalPercentages) / unPopulatedFields;
                for (Map.Entry<String, Double> genre : dto.getGenres().entrySet()) {
                    if (genre.getValue() == 0.00) {
                        genre.setValue(percentageLeft);
                    }
                }
            } else {
                double percentageLeft = (100 - totalPercentages) / dto.getGenres().keySet().size();
                for (Map.Entry<String,Double> genre: dto.getGenres().entrySet()) {
                    genre.setValue(genre.getValue() + percentageLeft);
                }
            }
        }




        // If there is at least one genre added by the user
            Playlist playlist = playlistMapper.fromDto(dto, user);
            double[] distanceAndDuration = bingMapService.getLocations(dto.getFrom(), dto.getTo());
            Map<String, Double> genresDurations = populatePlaytimeForEachGenre(dto, distanceAndDuration);
            Map<String, Integer> averagePlaytimeForGenre = calculatingAverageTimePerGenre(genresDurations);
            List<Track> trackSet = new ArrayList<>();
            fetchTracksFromDatabase(dto, genresDurations, averagePlaytimeForGenre, trackSet);
            long avgRank = 0;
            int totalPlaytime = 0;

            for (Track track: trackSet) {
                avgRank += track.getRank();
                totalPlaytime += track.getPlaytime();
            }
            totalPlaytime /= 60;

            totalPlaytime = removeTracksTillPlaytimeInRange(totalPlaytime, distanceAndDuration, trackSet);

            playlist.setPlaytime(totalPlaytime);
            playlist.setRank(avgRank / trackSet.size());
            playlist.setTrackSet(trackSet);
            playlist.setPhotoUrl(pixabayService.getPlaylistCoverUrl());
            return playlistRepository.save(playlist);
    }


    private static int removeTracksTillPlaytimeInRange(int totalPlaytime, double[] distanceAndDuration, List<Track> trackSet) {
        while(totalPlaytime > distanceAndDuration[1]+5){
            totalPlaytime -=(trackSet.get(trackSet.size()-1).getPlaytime()/60);
            trackSet.remove(trackSet.size()-1);
        }
        return totalPlaytime;
    }

    private Map<String, Integer> calculatingAverageTimePerGenre(Map<String, Double> genresDurations) {
        Map<String, Integer> averagePlaytimeForGenre = new HashMap<>();
        for (String genre : genresDurations.keySet()) {
            averagePlaytimeForGenre.put(genre, trackRepository.findAveragePlayTimeForGenre(genre));
        }
        return averagePlaytimeForGenre;
    }


    private Map<String, Double> populatePlaytimeForEachGenre(PlaylistDto dto, double[] distanceAndDuration) {
        int totalTravelTimeInSeconds = (int) distanceAndDuration[1] * 60;

        Map<String, Double> genresDurations = new HashMap<>();

        for (Map.Entry<String, Double> genre: dto.getGenres().entrySet()) {
//            if (genre.getValue() == 0.00) {
//                continue;
//            }
            genresDurations.put(genre.getKey(), genre.getValue() * totalTravelTimeInSeconds / 100);
        }
        return genresDurations;
    }

    private void fetchTracksFromDatabase(PlaylistDto dto, Map<String, Double> genresDurations, Map<String, Integer> averagePlaytimeForGenre, List<Track> trackSet) {
        for (String genre : genresDurations.keySet()) {
            Set<Track> result;
            if (dto.isTracksFromSameArtist() && dto.isUseTopTracks()) {
                result = trackRepository.findTopTrackByGenre(genre,
                        (int) (genresDurations.get(genre) / averagePlaytimeForGenre.get(genre)) + BUFFER_TRACKS);
            } else if (dto.isTracksFromSameArtist()) {
                result = trackRepository.findTrackByGenre(genre,
                        (int) (genresDurations.get(genre) / averagePlaytimeForGenre.get(genre)) + BUFFER_TRACKS);
            } else if (dto.isUseTopTracks()) {
                result = trackRepository.findTopTrackByGenreAndDistinctArtist(genre,
                        (int) (genresDurations.get(genre) / averagePlaytimeForGenre.get(genre)) + BUFFER_TRACKS);
            } else {
                result = trackRepository.findTrackByGenreAndDistinctArtist(genre,
                        (int) (genresDurations.get(genre) / averagePlaytimeForGenre.get(genre)) + BUFFER_TRACKS);
            }
            trackSet.addAll(result);
        }
    }


    @Override
    public void updatePlaylist(Playlist playlist, User user) {
        if (user.getId() != playlist.getCreator().getId()) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_MSG);
        }
        playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(int id, User user) {
        Playlist playlist = playlistRepository.findById(id);
        if(playlist==null){
            throw new EntityNotFoundException("Playlist", id);
        }
        if (user.getId() != playlist.getCreator().getId()) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_MSG);
        }
        playlistRepository.delete(playlist);
    }
}
