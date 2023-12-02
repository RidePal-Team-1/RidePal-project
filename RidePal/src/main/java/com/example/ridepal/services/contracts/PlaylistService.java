package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface PlaylistService {

    Page<Playlist> findAll(String titleFilter, String genre, String minDuration, String maxDuration, Pageable pageable);

    Playlist getPlaylistById(int id);

    void updatePlaylist(Playlist playlist, User user);

    void deletePlaylist(int id, User user);

    Playlist createPlaylist(PlaylistDto dto, User user);

}
