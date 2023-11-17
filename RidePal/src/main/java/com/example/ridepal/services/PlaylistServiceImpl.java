package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import static com.example.ridepal.filters.specifications.PlaylistSpecifications.*;


@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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

    @Override
    public void createPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }
}
