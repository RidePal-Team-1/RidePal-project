package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Track;
import com.example.ridepal.models.TrackResult;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.services.contracts.BingMapService;
import com.example.ridepal.services.contracts.PixabayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTests {

    @InjectMocks
    PlaylistServiceImpl playlistService;

    @Mock
    PlaylistRepository playlistRepository;

    @Mock
    PlaylistMapper playlistMapper;

    @Mock
    BingMapService bingMapService;

    @Mock
    TrackRepository trackRepository;

    @Mock
    PixabayService pixabayService;

    @Test
    public void getPlaylistById_Should_Throw_WhenPlaylistNotFound() {
        //Arrange
        Mockito.when(playlistRepository.findById(Mockito.anyInt())).thenReturn(null);
        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> playlistService.getPlaylistById(Mockito.anyInt()));
    }

    @Test
    public void getPlaylistById_Should_CallRepository() {

        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        Mockito.when(playlistRepository.findById(Mockito.anyInt())).thenReturn(mockPlaylist);

        //Act
        playlistService.getPlaylistById(Mockito.anyInt());

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(2)).findById(Mockito.anyInt());
    }

    @Test
    public void createPlaylist_Should_CallRepository() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto mockDto = Helpers.createMockPlaylistDto();
        User mockUser = Helpers.createMockUser();
        Mockito.when(playlistMapper.fromDto(mockDto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(Mockito.anyString(), Mockito.anyString())).thenReturn(Mockito.any());
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(Mockito.anyString())).thenReturn(Mockito.anyInt());
        Mockito.when(trackRepository.findTopTrackByGenre(Mockito.anyString(), Mockito.anyInt())).thenReturn(Mockito.any());
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn(Mockito.anyString());

        //Act
        playlistService.createPlaylist(mockDto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);

    }
}
