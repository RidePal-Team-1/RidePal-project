package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.PlaylistSortField;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.services.contracts.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/api/playlists")
@Tag(name = "Playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    private final PlaylistMapper playlistMapper;

    @Autowired
    public PlaylistController(PlaylistService playlistService, PlaylistMapper playlistMapper) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
    }

    @GetMapping
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Retrieve playlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found zero or more playlists",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Playlist.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)})
    public Page<Playlist> findAll(@RequestParam(required = false) String title,
                                          @RequestParam(required = false) String genre,
                                          @RequestParam(required = false) String minDuration,
                                          @RequestParam(required = false) String maxDuration,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int sizePerPage,
                                          @RequestParam(defaultValue = "ID") PlaylistSortField sortField,
                                          @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());

        return playlistService.findAll(title, genre, minDuration, maxDuration, pageable);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Retrieve playlist by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid id supplied",
                    content = @Content) })
    public Playlist findById(@PathVariable int id) {
        try {
            return playlistService.getPlaylistById(id);
        } catch (EntityNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Generate a playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist generated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)})
    public Playlist create(@Valid @RequestBody PlaylistDto dto, Principal principal) {
           return playlistService.createPlaylist(dto, principal);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Update playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid id supplied",
                    content = @Content) })
    public void update(@PathVariable int id, @RequestBody PlaylistDto playlistDto) {
        try {
            Playlist playlist = playlistMapper.fromDto(playlistDto, id);
            playlistService.updatePlaylist(playlist);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "basicAuth")
    @Operation(summary = "Delete playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid id supplied",
                    content = @Content) })
    public void delete(@PathVariable int id) {
        try {
            playlistService.deletePlaylist(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
