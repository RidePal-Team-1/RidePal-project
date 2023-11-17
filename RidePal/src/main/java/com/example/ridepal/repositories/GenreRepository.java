package com.example.ridepal.repositories;

import com.example.ridepal.models.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Genre findById(int id);
}
