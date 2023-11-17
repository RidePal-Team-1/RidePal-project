package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistService {

    Page<Playlist> getAllPlaylists(String title, String genre, double minRank, double maxRank, Pageable pageable);

    Playlist getPlaylistById(int id);

    void updatePlaylist(Playlist playlist);

    void deletePlaylist(int id);

    void createPlaylist(Playlist playlist);

}
