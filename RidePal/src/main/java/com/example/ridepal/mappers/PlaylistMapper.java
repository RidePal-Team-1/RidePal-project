package com.example.ridepal.mappers;

import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.models.dtos.PlaylistUpdateDto;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.UserRepository;
import com.example.ridepal.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Component
public class PlaylistMapper {

    private final PlaylistRepository playlistRepository;

    private final UserRepository userRepository;

    private final GenreRepository genreRepository;

    @Autowired
    public PlaylistMapper(UserRepository userRepository,
                          PlaylistRepository playlistRepository,
                          GenreRepository genreRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
    }

    public Playlist fromDto(PlaylistUpdateDto dto, int id) {
        Playlist playlist = playlistRepository.findById(id);
        playlist.setTitle(dto.getTitle());
        return playlist;
    }


    public Playlist fromDto(PlaylistDto dto, User user) {
        Playlist playlist = new Playlist();
        playlist.setTitle(dto.getTitle());
        Set<Genre> genres = new HashSet<>();
        for (String genre: dto.getGenres().keySet()) {
            genres.add(genreRepository.findByName(genre));
        }
        playlist.setGenres(genres);
        playlist.setCreator(userRepository.findByEmail(user.getEmail()));
        return playlist;
    }

    //TODO trackset, creator and rank, playtime should be set after the playlist is generated
}