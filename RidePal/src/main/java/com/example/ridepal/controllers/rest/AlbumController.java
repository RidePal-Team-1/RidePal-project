package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Album;
import com.example.ridepal.services.contracts.AlbumService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@Hidden
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> findAll(){
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public Album findById(@PathVariable long id){
        try{
            return albumService.findById(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
