package com.example.ridepal.mappers;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistMapper(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public Playlist fromDto(PlaylistDto dto, int id) {
        Playlist playlist = playlistService.getPlaylistById(id);
        playlist.setTitle(dto.getTitle());
        return playlist;
    }
}