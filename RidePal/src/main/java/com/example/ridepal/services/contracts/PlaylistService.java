package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Playlist;

import java.util.List;

public interface PlaylistService {

    List<Playlist> getAllPlaylists();

    Playlist getPlaylistById(int id);

    void updatePlaylist(Playlist playlist);

    void deletePlaylist(int id);

    void createPlaylist(Playlist playlist);
}
