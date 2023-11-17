package com.example.ridepal.services;

import com.example.ridepal.models.Artist;
import com.example.ridepal.repositories.ArtistRepository;
import com.example.ridepal.services.contracts.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }
}
