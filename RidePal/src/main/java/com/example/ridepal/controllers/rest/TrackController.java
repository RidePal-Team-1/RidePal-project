package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.TrackSortField;
import com.example.ridepal.models.Track;
import com.example.ridepal.services.contracts.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Sort sort = Sort.by(Sort.Direction.ASC, TrackSortField.RANK.getDatabaseFieldName());
        return trackService.findAll(sort);
    }

    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable int id) {
        try {
            return trackService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
