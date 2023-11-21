package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Track;
import com.example.ridepal.models.TrackResult;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.repositories.UserRepository;
import com.example.ridepal.services.contracts.BingMapService;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.example.ridepal.filters.specifications.PlaylistSpecifications.*;


@Service
public class PlaylistServiceImpl implements PlaylistService {

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

        int totalTravelTimeInSeconds = (int) distanceAndDuration[1] * 60;

        Map<String, Double> genresDurations = new HashMap<>();

        for (Map.Entry<String, Double> genre: dto.getGenres().entrySet()) {
            genresDurations.put(genre.getKey(), genre.getValue() * totalTravelTimeInSeconds / 100);
        }

        Map<String, Integer> averagePlaytimeForGenre = new HashMap<>();
        Set<Track> trackSet = new HashSet<>();
        for (String genre : genresDurations.keySet()) {
            averagePlaytimeForGenre.put(genre, trackRepository.findAveragePlayTimeForGenre(genre));
        }

        for (String genre: genresDurations.keySet()) {
           Set<Track> result =  trackRepository.findTrackByGenre(genre, (int) (genresDurations.get(genre) / averagePlaytimeForGenre.get(genre)));
           trackSet.addAll(result);
        }
        //add +1 to limit and if you have to remove just remove it.

        long avgRank = 0;
        int totalPlaytime = 0;

        for (Track track: trackSet) {
            avgRank += track.getRank();
            totalPlaytime += track.getPlaytime();
        }

        totalPlaytime /= 60;

        playlist.setPlaytime(totalPlaytime);
        playlist.setRank(avgRank / trackSet.size());
        playlist.setTrackSet(trackSet);

        return playlistRepository.save(playlist);
        //if he chooses 1 genre only do not show field for percentages
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
