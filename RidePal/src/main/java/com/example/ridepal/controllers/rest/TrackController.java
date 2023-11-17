package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.Track;
import com.example.ridepal.services.contracts.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService tracksService) {
        this.trackService = tracksService;
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable int id) {
        return trackService.getTrackById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable int id) {
        trackService.deleteTrack(id);
    }
}
