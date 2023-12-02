package com.example.ridepal;

import com.example.ridepal.filters.enums.Provider;
import com.example.ridepal.models.Album;
import com.example.ridepal.models.Artist;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
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
    mockUser.setPassword("mockPassowrd");
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
}
