package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Album;
import com.example.ridepal.repositories.AlbumRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTests {


    @Mock
    AlbumRepository mockRepository;

    @InjectMocks
    AlbumServiceImpl albumService;


    @Test
    public void findAll_Should_CallRepository() {

        //Act
        albumService.findAll();

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_Should_Throw_WhenAlbumDoesNotExist() {
        //Arrange
        Mockito.when(mockRepository.findById(Mockito.anyLong())).thenReturn(null);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> albumService.findById(Mockito.anyLong()));
    }

    @Test
    public void findById_Should_ReturnAlbumWhenValidId() {
        //Arrange
        Album mockAlbum = Helpers.createMockAlbum();

        //Act
        Mockito.when(mockRepository.findById(Mockito.anyLong())).thenReturn(mockAlbum);
        Album result = albumService.findById(Mockito.anyLong());

        //Assert
        Assertions.assertEquals(result, mockAlbum);
    }

    @Test
    public void findById_Should_CallRepository() {
        //Arrange
        Album mockAlbum = Helpers.createMockAlbum();
        Mockito.when(mockRepository.findById(Mockito.anyLong())).thenReturn(mockAlbum);

        //Act
        albumService.findById(Mockito.anyLong());

        //Assert
        Mockito.verify(mockRepository, Mockito.times(2)).findById(Mockito.anyLong());
    }

}
