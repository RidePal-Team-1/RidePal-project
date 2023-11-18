package com.example.ridepal.controllers.rest;

import com.example.ridepal.deezer.DeezerGenre;
import com.example.ridepal.services.contracts.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deezer")
public class DeezerController {

    private final DeezerService deezerService;

    @Autowired
    public DeezerController(DeezerService deezerService) {
        this.deezerService = deezerService;
    }

    @GetMapping("/genres")
    public void getGenres() {
        deezerService.getGenres();
    }

    @GetMapping(value = "/playlists")
    public void getPlaylists(@RequestParam(required = true) String genre){ deezerService.getPlaylists(genre);}
}
