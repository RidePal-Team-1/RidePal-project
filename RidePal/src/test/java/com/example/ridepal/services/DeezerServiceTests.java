package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.deezer.DeezerGenresResponse;
import com.example.ridepal.deezer.DeezerPlaylistResponse;
import com.example.ridepal.deezer.DeezerTrackResponse;
import com.example.ridepal.external.services.DeezerServiceImpl;
import com.example.ridepal.models.Album;
import com.example.ridepal.models.Artist;
import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Track;
import com.example.ridepal.repositories.AlbumRepository;
import com.example.ridepal.repositories.ArtistRepository;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.TrackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DeezerServiceTests {

    @InjectMocks
    DeezerServiceImpl deezerService;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    TrackRepository trackRepository;

    @Mock
    RestTemplate restTemplate;


//    @Test
//    public void getPlaylists_Should_CallSavePlaylistsToDatabase() {
//
//        //Arrange
//        String url = "MockUrl";
//        String genre = "mockGenre";
//        DeezerPlaylistResponse response = Helpers.createMockPlaylistResponse();
//        response.setData(List.of(Helpers.createMockDeezerPlaylist()));
//        DeezerTrackResponse trackResponse = Helpers.createMockTrackResponse();
//        trackResponse.setData(List.of(Helpers.createMockDeezerTrack()));
//        Mockito.when(restTemplate.getForObject(url, DeezerPlaylistResponse.class)).thenReturn(response);
//        Mockito.when(restTemplate.getForObject(url, DeezerTrackResponse.class)).thenReturn(trackResponse);
//        Mockito.doNothing().when(artistRepository).save(Mockito.any(Artist.class));
//        Mockito.doNothing().when(albumRepository).save(Mockito.any(Album.class));
//        Mockito.when(genreRepository.findByName(genre)).thenReturn(Mockito.any(Genre.class));
//        Mockito.doNothing().when(trackRepository).save(Mockito.any(Track.class));
//
//        //Act
//        deezerService.getPlaylists(genre);
//
//        //Assert
//        Mockito.verify(trackRepository, Mockito.times(1)).save(Mockito.any(Track.class));
//    }

}
