package com.example.ridepal.services;

import com.example.ridepal.external.services.DeezerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
