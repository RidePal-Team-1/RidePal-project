package com.example.ridepal.controllers.rest;

import com.example.ridepal.filters.enums.GenreSortField;
import com.example.ridepal.models.Genre;
import com.example.ridepal.services.contracts.GenreService;
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
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Page<Genre> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int sizePerPage,
                               @RequestParam(defaultValue = "ID") GenreSortField sortField,
                               @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        return genreService.findAll(pageable);
    }
}
