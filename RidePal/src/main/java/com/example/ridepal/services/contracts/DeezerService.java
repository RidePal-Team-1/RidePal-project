package com.example.ridepal.services.contracts;

import com.example.ridepal.deezer.DeezerGenre;

import java.util.List;

public interface DeezerService {

    void getGenres();

    void getPlaylists(String q);

    void getTracks();
}
