package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.*;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.RoleRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Mock
    RoleRepository roleRepository;

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
    public void createPlaylist_Should_CallRepository_WhenUserSelectGenresAndCheckedSameArtistsAndTopTracks() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = new PlaylistDto();
        dto.setFrom("mockDestination");
        dto.setTo("mockDestination");
        dto.setTitle("mockTitle");
        dto.setUseTopTracks(true);
        dto.setTracksFromSameArtist(true);
        dto.setGenres(new HashMap<>(Map.of("mockGenre", 100.00)));
        User mockUser = Helpers.createMockUser();
        String genreFromDto = dto.getGenres().keySet().iterator().next();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(genreFromDto)).thenReturn(4);
        Mockito.when(trackRepository.findTopTrackByGenre(genreFromDto,6087)).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);

    }

    @Test
    public void createPlaylist_Should_CallRepository_WhenUserSelectGenresAndCheckedOnlyTopTracks() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = new PlaylistDto();
        dto.setFrom("mockDestination");
        dto.setTo("mockDestination");
        dto.setTitle("mockTitle");
        dto.setUseTopTracks(true);
        dto.setTracksFromSameArtist(false);
        dto.setGenres(new HashMap<>(Map.of("mockGenre", 100.00)));
        User mockUser = Helpers.createMockUser();
        String genreFromDto = dto.getGenres().keySet().iterator().next();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(genreFromDto)).thenReturn(4);
        Mockito.when(trackRepository.findTopTrackByGenreAndDistinctArtist(genreFromDto,6087)).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);

    }

    @Test
    public void createPlaylist_Should_CallRepository_WhenUserSelectGenresAndCheckedОnlySameArtists() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = new PlaylistDto();
        dto.setFrom("mockDestination");
        dto.setTo("mockDestination");
        dto.setTitle("mockTitle");
        dto.setUseTopTracks(false);
        dto.setTracksFromSameArtist(true);
        dto.setGenres(new HashMap<>(Map.of("mockGenre", 100.00)));
        User mockUser = Helpers.createMockUser();
        String genreFromDto = dto.getGenres().keySet().iterator().next();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(genreFromDto)).thenReturn(4);
        Mockito.when(trackRepository.findTrackByGenre(genreFromDto,6087)).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);

    }

    @Test
    public void createPlaylist_Should_CallRepository_WhenUserSelectGenres() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = new PlaylistDto();
        dto.setFrom("mockDestination");
        dto.setTo("mockDestination");
        dto.setTitle("mockTitle");
        dto.setUseTopTracks(false);
        dto.setTracksFromSameArtist(false);
        dto.setGenres(new HashMap<>(Map.of("mockGenre", 100.00)));
        User mockUser = Helpers.createMockUser();
        String genreFromDto = dto.getGenres().keySet().iterator().next();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(genreFromDto)).thenReturn(4);
        Mockito.when(trackRepository.findTrackByGenreAndDistinctArtist(genreFromDto,6087)).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);

    }

    @Test
    public void createPlaylist_Should_CallRepository_WhenUserDidNotSelectGenres() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = Helpers.createMockPlaylistDto();
        dto.setGenres(null);
        User mockUser = Helpers.createMockUser();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(Mockito.any())).thenReturn(4);
        Mockito.when(trackRepository.findTopTrackByGenre(Mockito.any(), Mockito.anyInt())).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);
    }


    @Test
    public void createPlaylist_Should_CallRepository_WhenUserLeftUnpopulatedFields() {
        //Arrange
        Playlist mockPlaylist = Helpers.createMockPlaylist();
        PlaylistDto dto = Helpers.createMockPlaylistDto();
        dto.setGenres(new HashMap<>(Map.of("mockGenre2", 0.00)));
        String genreFromDto = dto.getGenres().keySet().iterator().next();
        User mockUser = Helpers.createMockUser();
        Mockito.when(playlistMapper.fromDto(dto, mockUser)).thenReturn(mockPlaylist);
        Mockito.when(bingMapService.getLocations(dto.getFrom(), dto.getTo())).thenReturn(( new double[] {220, 405}));
        Mockito.when(trackRepository.findAveragePlayTimeForGenre(genreFromDto)).thenReturn(4);
        Mockito.when(trackRepository.findTopTrackByGenre(genreFromDto, 6087)).thenReturn(Set.of(Helpers.createMockTrack()));
        Mockito.when(pixabayService.getPlaylistCoverUrl()).thenReturn("mockUrl");

        //Act
        playlistService.createPlaylist(dto, mockUser);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(mockPlaylist);
    }

    @Test
    public void updatePlaylist_Should_CallRepository() {
        //Arrange
        User user = Helpers.createMockUser();
        Playlist playlist = Helpers.createMockPlaylist();

        //Act
        playlistService.updatePlaylist(playlist, user);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(playlist);
    }

    @Test
    public void updatePlaylist_Should_Throw_WhenUserIsNotCreator() {
        //Arrange
        User user = Helpers.createMockUser();
        user.setId(2);
        Playlist playlist = Helpers.createMockPlaylist();

        //Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> playlistService.updatePlaylist(playlist, user));
    }

    @Test
    public void deletePlaylist_Should_CallRepository() {
        //Arrange
        Playlist playlist = Helpers.createMockPlaylist();
        User user = Helpers.createMockUser();
        Role adminRole = Helpers.createMockAdminRole();
        Mockito.when(playlistRepository.findById(Mockito.anyInt())).thenReturn(playlist);

        //Act
        playlistService.deletePlaylist(1, user);


        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).delete(playlist);
    }

    @Test
    public void deletePlaylist_Throw_WhenPlaylistNotFound() {
        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(playlistRepository.findById(Mockito.anyInt())).thenThrow(EntityNotFoundException.class);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> playlistService.deletePlaylist(1, mockUser));
    }



}
