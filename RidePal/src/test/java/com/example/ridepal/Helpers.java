package com.example.ridepal;

import com.example.ridepal.deezer.DeezerGenre;
import com.example.ridepal.deezer.DeezerGenresResponse;
import com.example.ridepal.deezer.DeezerPlaylist;
import com.example.ridepal.deezer.DeezerPlaylistResponse;
import com.example.ridepal.filters.enums.Provider;
import com.example.ridepal.models.*;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Helpers {

 public static Album createMockAlbum() {
    var mockAlbum = new Album();
    mockAlbum.setId(1);
    mockAlbum.setPhotoUrl(null);
    mockAlbum.setName("testName");
    mockAlbum.setUrl("customUrl");
    return mockAlbum;
    }

    public static Artist createMockArtist() {
    var mockArtist = new Artist();
    mockArtist.setPhotoUrl(null);
    mockArtist.setId(1);
    mockArtist.setName("testName");
    mockArtist.setTrackListUrl("customUrl");
    return mockArtist;
    }

    public static User createMockUser() {
    var mockUser = new User();
    mockUser.setRoles(Helpers.createMockRoles());
    mockUser.setEmail("mockMail");
    mockUser.setProvider(Provider.LOCAL);
    mockUser.setId(1);
    mockUser.setPassword("mockPassword");
    mockUser.setFirstName("mockFirstName");
    mockUser.setLastName("mockLastName");
    mockUser.setUsername("mockUsername");
    mockUser.setProfile_picture(null);
    return mockUser;
    }

    public static Set<Role> createMockRoles() {
    Set<Role> mockRoles = new HashSet<>();
    mockRoles.add(createMockUserRole());
    return mockRoles;
    }

    public static Role createMockUserRole() {
     var mockRole = new Role();
     mockRole.setId(1);
     mockRole.setName("USER");
     return mockRole;
    }

    public static Role createMockAdminRole() {
    var mockRole = new Role();
    mockRole.setId(2);
    mockRole.setName("ADMIN");
    return mockRole;
    }

    public static Playlist createMockPlaylist() {
    var mockPlaylist = new Playlist();
    mockPlaylist.setCreator(Helpers.createMockUser());
    mockPlaylist.setRank(1);
    mockPlaylist.setPlaytime(306);
    mockPlaylist.setId(1);
    mockPlaylist.setTitle("mockTitle");
    mockPlaylist.setGenres(Set.of(createMockGenre()));
    mockPlaylist.setTrackSet(List.of(createMockTrack()));
    return mockPlaylist;
    }

    public static Genre createMockGenre() {
    var mockGenre = new Genre();
    mockGenre.setId(1);
    mockGenre.setName("mockGenre");
    mockGenre.setPhotoUrl("mockUrl");
    return mockGenre;
    }

    public static Track createMockTrack() {
    var mockTrack = new Track();
    mockTrack.setRank(1);
    mockTrack.setPlaytime(1);
    mockTrack.setTitle("mockTitle");
    mockTrack.setGenre(createMockGenre());
    mockTrack.setUrl("mockUrl");
    mockTrack.setAlbum(createMockAlbum());
    mockTrack.setArtist(createMockArtist());
    return mockTrack;
    }

    public static DeezerGenresResponse createMockGenreResponse() {
    var mockResponse = new DeezerGenresResponse();
    mockResponse.setData(List.of(createMockDeezerGenre()));
    return mockResponse;
    }

    public static DeezerPlaylistResponse createMockPlaylistResponse() {
    var mockResponse = new DeezerPlaylistResponse();
    mockResponse.setData(List.of(createMockDeezerPlaylist()));
    return mockResponse;
    }

    public static DeezerGenre createMockDeezerGenre() {
      var mockDeezerGenre = new DeezerGenre();
      mockDeezerGenre.setId(1);
      mockDeezerGenre.setName("mockGenre");
      mockDeezerGenre.setPhotoUrl("mockGenre");
      return mockDeezerGenre;
    }

    public static DeezerPlaylist createMockDeezerPlaylist() {
    var mockDeezerPlaylist = new DeezerPlaylist();
    mockDeezerPlaylist.setTrackList("mockTrackList");
    return mockDeezerPlaylist;
    }

    public static PlaylistDto createMockPlaylistDto() {
    PlaylistDto dto = new PlaylistDto();
    dto.setFrom("mockDestination");
    dto.setTo("mockDestination");
    dto.setTitle("mockTitle");
    dto.setUseTopTracks(true);
    dto.setTracksFromSameArtist(true);
    dto.setGenres(Map.of("mockGenre", 2.00));
    return dto;
    }
}
