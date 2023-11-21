package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.dtos.PlaylistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface PlaylistService {

    Page<Playlist> findAll(String titleFilter, String genre, String minDuration, String maxDuration, Pageable pageable);

    Playlist getPlaylistById(int id);

    void updatePlaylist(Playlist playlist);

    void deletePlaylist(int id);

    Playlist createPlaylist(PlaylistDto dto, Principal principal);
}
