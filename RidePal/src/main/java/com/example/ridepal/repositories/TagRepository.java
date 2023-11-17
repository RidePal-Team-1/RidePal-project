package com.example.ridepal.repositories;

import com.example.ridepal.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Genre, Integer> {
}
