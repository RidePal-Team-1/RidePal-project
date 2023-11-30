package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.helpers.AuthenticationHelper;
import com.example.ridepal.models.User;
import com.example.ridepal.services.contracts.DeezerService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/deezer")
@Hidden
public class DeezerController {

    private final DeezerService deezerService;

    @Autowired
    public DeezerController(DeezerService deezerService) {
        this.deezerService = deezerService;
    }

    @GetMapping("/genres")
    public void getGenres(Authentication authentication) {
            deezerService.getGenres();
    }

    @GetMapping(value = "/playlists")
    public void getPlaylists(@RequestParam(required = true) String genre){ deezerService.getPlaylists(genre);}
}
