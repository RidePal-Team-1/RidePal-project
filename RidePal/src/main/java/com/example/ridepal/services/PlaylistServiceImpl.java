package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Track;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.services.contracts.BingMapService;
import com.example.ridepal.services.contracts.PlaylistService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.*;

import static com.example.ridepal.filters.specifications.PlaylistSpecifications.*;


@Service
public class PlaylistServiceImpl implements PlaylistService {

    public static final int BUFFER_TRACKS = 12;
    private final PlaylistRepository playlistRepository;

    private final BingMapService bingMapService;

    private final PlaylistMapper playlistMapper;

    private final TrackRepository trackRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository,
                               BingMapService bingMapService,
                               PlaylistMapper playlistMapper,
                               TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.bingMapService = bingMapService;
        this.playlistMapper = playlistMapper;
        this.trackRepository = trackRepository;
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
    public Playlist createPlaylist(PlaylistDto dto, Principal principal) {
        Playlist playlist = playlistMapper.fromDto(dto, principal);

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
        return playlistRepository.save(playlist);
    }

    private static int removeTracksTillPlaytimeInRange(int totalPlaytime, double[] distanceAndDuration, List<Track> trackSet) {
        while(totalPlaytime > distanceAndDuration[1]+5){
            totalPlaytime -=(trackSet.get(trackSet.size()-1).getPlaytime()/60);
            trackSet.remove(trackSet.size()-1);
        }
        return totalPlaytime;
    }

    @NotNull
    private Map<String, Integer> calculatingAverageTimePerGenre(Map<String, Double> genresDurations) {
        Map<String, Integer> averagePlaytimeForGenre = new HashMap<>();
        for (String genre : genresDurations.keySet()) {
            averagePlaytimeForGenre.put(genre, trackRepository.findAveragePlayTimeForGenre(genre));
        }
        return averagePlaytimeForGenre;
    }

    @NotNull
    private static Map<String, Double> populatePlaytimeForEachGenre(PlaylistDto dto, double[] distanceAndDuration) {
        int totalTravelTimeInSeconds = (int) distanceAndDuration[1] * 60;

        Map<String, Double> genresDurations = new HashMap<>();

        for (Map.Entry<String, Double> genre: dto.getGenres().entrySet()) {
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
    public void updatePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(int id) {
        Playlist playlist = playlistRepository.findById(id);
        if(playlist==null){
            throw new EntityNotFoundException("Playlist", id);
        }else
            playlistRepository.delete(playlist);
    }


}
