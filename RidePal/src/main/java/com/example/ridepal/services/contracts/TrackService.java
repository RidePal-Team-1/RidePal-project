package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Track;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TrackService {

    List<Track> findAll(Sort sort);

    Track findById(int id);
}
