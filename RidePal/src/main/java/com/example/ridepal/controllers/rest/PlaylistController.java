package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable int id) {
        return playlistService.getPlaylistById(id);
    }

    @PostMapping
    public void createPlaylist(@RequestBody PlaylistDto playlistDto){
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDto.getTitle());
        playlistService.createPlaylist(playlist);
    }

    @PutMapping("/{id}")
    public void updatePlaylist(@PathVariable int id, @RequestBody PlaylistDto playlistDto) {
        Playlist playlist = playlistService.getPlaylistById(id);
        playlist.setTitle(playlistDto.getTitle());
        playlistService.updatePlaylist(playlist);
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable int id) {
        playlistService.deletePlaylist(id);
    }
}
