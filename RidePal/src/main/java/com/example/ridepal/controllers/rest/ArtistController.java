package com.example.ridepal.controllers.rest;

import com.example.ridepal.filters.enums.ArtistSortField;
import com.example.ridepal.filters.enums.GenreSortField;
import com.example.ridepal.models.Artist;
import com.example.ridepal.services.contracts.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public Page<Artist> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int sizePerPage,
                                @RequestParam(defaultValue = "ID") ArtistSortField sortField,
                                @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page,sizePerPage,sortDirection,sortField.getDatabaseFieldName());
        return artistService.findAll(pageable);
    }
}
