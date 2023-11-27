package com.example.ridepal.repositories;

import com.example.ridepal.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Page<Genre> findAll(Pageable pageable);

    Genre findById(int id);

    Genre findByName(String genre);

    @Query(value = "select count(*) from genres", nativeQuery = true)
    int getGenresCount();

}
