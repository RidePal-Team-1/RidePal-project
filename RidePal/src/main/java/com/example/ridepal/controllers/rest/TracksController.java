package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.Track;
import com.example.ridepal.models.dtos.TrackDto;
import com.example.ridepal.services.contracts.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TracksController {
    private final TrackService trackService;

    public TracksController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Autowired


    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable int id) {
        return trackService.getTrackById(id);
    }

    @PostMapping
    public void createTrack(@RequestBody TrackDto trackDto){
        Track track = new Track();
        track.setTitle(trackDto.getTitle());
        track.setAlbum(trackDto.getAlbum());
        track.setArtist(trackDto.getArtist());
        trackService.createTrack(track);
    }

    @PutMapping("/{id}")
    public void updateTrack(@PathVariable int id, @RequestBody TrackDto trackDto) {
        Track track = trackService.getTrackById(id);
        track.setTitle(trackDto.getTitle());
        track.setAlbum(trackDto.getAlbum());
        track.setArtist(trackDto.getArtist());
        trackService.updateTrack(track);
    }

    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable int id) {
        trackService.deleteTrack(id);
    }
}
