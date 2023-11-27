package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Artist;
import com.example.ridepal.repositories.AlbumRepository;
import com.example.ridepal.repositories.ArtistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.HandlerExceptionResolver;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTests {

    @Mock
    ArtistRepository mockRepository;

    @InjectMocks
    ArtistServiceImpl artistService;

    @Test
    public void findAll_Should_CallRepository() {

        //Arrange
        Pageable pageable = PageRequest.of(10, 20);
        Mockito.when(mockRepository.findAll(pageable)).thenReturn(null);

        //Act
        artistService.findAll(pageable);

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findAll(pageable);
    }


    @Test
    public void findById_Should_Throw_WhenInvalidId() {


        //Arrange
        Mockito.when(mockRepository.findById(Mockito.anyLong())).thenReturn(null);


        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> artistService.findById(2));
    }

    @Test
    public void findById_Should_CallRepositoryWhenIdIsValid() {
        Artist mockArtist = Helpers.createMockArtist();
        //Arrange
        Mockito.when(mockRepository.findById(Mockito.anyLong())).thenReturn(mockArtist);

        //Act
        Artist result = artistService.findById(Mockito.anyLong());

        //Assert
        Assertions.assertEquals(result, mockArtist);
    }
}
