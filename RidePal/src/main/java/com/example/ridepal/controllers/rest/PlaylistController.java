package com.example.ridepal.controllers.rest;

import com.example.ridepal.filters.enums.PlaylistSortField;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public Page<Playlist> findAll(@RequestParam(required = false) String title,
                                          @RequestParam(required = false) String genre,
                                          @RequestParam(required = false) String minDuration,
                                          @RequestParam(required = false) String maxDuration,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int sizePerPage,
                                          @RequestParam(defaultValue = "ID") PlaylistSortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());

        return playlistService.findAll(title, genre, minDuration, maxDuration, pageable);
    }

    @GetMapping("/{id}")
    public Playlist findById(@PathVariable int id) {

        return playlistService.getPlaylistById(id);
    }

    @PostMapping
    public void create(@RequestBody PlaylistDto playlistDto){
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDto.getTitle());
        playlistService.createPlaylist(playlist);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody PlaylistDto playlistDto) {
        Playlist playlist = playlistService.getPlaylistById(id);
        playlist.setTitle(playlistDto.getTitle());
        playlistService.updatePlaylist(playlist);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        playlistService.deletePlaylist(id);
    }
}
