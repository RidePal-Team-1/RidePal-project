package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Track;

import java.util.List;

public interface TrackService {

    List<Track> getAllTracks();

    Track getTrackById(int id);

    void createTrack(Track track);

    void updateTrack(Track track);

    void deleteTrack(int id);
}
