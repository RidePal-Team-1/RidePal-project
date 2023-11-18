package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {

    Page<Genre> findAll(Pageable pageable);

    Genre findById(int id);
}
