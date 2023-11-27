package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Album;

import java.util.List;

public interface AlbumService {

    List<Album> findAll();

    Album findById(long id);
}
