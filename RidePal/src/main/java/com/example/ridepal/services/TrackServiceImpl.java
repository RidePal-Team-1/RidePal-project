package com.example.ridepal.services;

import com.example.ridepal.models.Track;
import com.example.ridepal.services.contracts.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {
    @Override
    public List<Track> getAllTracks() {
        return null;
    }

    @Override
    public Track getTrackById(int id) {
        return null;
    }

    @Override
    public void createTrack(Track track) {

    }

    @Override
    public void updateTrack(Track track) {

    }

    @Override
    public void deleteTrack(int id) {

    }
}
