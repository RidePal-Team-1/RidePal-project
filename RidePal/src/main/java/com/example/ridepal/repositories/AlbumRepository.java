package com.example.ridepal.repositories;

import com.example.ridepal.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findAll();

    Album findById(long id);
}