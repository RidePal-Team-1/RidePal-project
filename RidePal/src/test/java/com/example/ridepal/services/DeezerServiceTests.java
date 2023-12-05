package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.deezer.DeezerGenresResponse;
import com.example.ridepal.deezer.DeezerPlaylistResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

public class DeezerServiceTests {

    @InjectMocks
    DeezerServiceImpl deezerService;

    @Mock
    RestTemplate restTemplate;

//    @Test
//    public void getPlaylists_Should_CallSavePlaylistsToDatabase() {
//
//        //Arrange
//        DeezerPlaylistResponse response = Helpers.createMockPlaylistResponse();
//        Mockito.when(restTemplate.getForObject(Mockito.anyString(), DeezerPlaylistResponse.class)).thenReturn(response);
//
//        //Act
//        deezerService.getPlaylists(Mockito.anyString());
//
//        //Assert
//        Mockito.verify()
//    }
}
