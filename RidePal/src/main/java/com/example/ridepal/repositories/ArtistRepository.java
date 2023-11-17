package com.example.ridepal.repositories;

import com.example.ridepal.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
