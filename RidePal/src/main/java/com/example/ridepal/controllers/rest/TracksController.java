package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Track;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.models.dtos.TrackDto;
import com.example.ridepal.services.contracts.TracksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TracksController {
    private final TracksService tracksService;
    @Autowired
    public TracksController(TracksService tracksService) {
        this.tracksService = tracksService;
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return tracksService.getAllTracks();
    }

    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable int id) {
        return tracksService.getTrackById(id);
    }

    @PostMapping
    public void createTrack(@RequestBody TrackDto trackDto){
        Track track = new Track();
        track.setTitle(trackDto.getTitle());
        track.setAlbum(trackDto.getAlbum());
        track.setArtist(trackDto.getArtist());
        tracksService.createTrack(track);
    }

    @PutMapping("/{id}")
    public void updateTrack(@PathVariable int id, @RequestBody TrackDto trackDto) {
        Track track = tracksService.getTrackById(id);
        track.setTitle(trackDto.getTitle());
        track.setAlbum(trackDto.getAlbum());
        track.setArtist(trackDto.getArtist());
        tracksService.updateTrack(track);
    }

    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable int id) {
        tracksService.deleteTrack(id);
    }
}
