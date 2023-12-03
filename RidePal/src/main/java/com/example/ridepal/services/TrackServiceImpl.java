package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Track;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.services.contracts.TrackService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Track> findAll(Sort sort) {
        return trackRepository.findAll(sort);
    }

    @Override
    public Track findById(int id) {
        if(trackRepository.findById(id)==null){
            throw new EntityNotFoundException("Track",id);
        }
        else {
            return trackRepository.findById(id);
        }
    }
}
