package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistService {

    Page<Artist> findAll(Pageable pageable);

    Artist findById(int id);
}
