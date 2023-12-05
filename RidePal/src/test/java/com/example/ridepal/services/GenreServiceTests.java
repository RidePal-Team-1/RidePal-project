package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.models.Genre;
import com.example.ridepal.repositories.GenreRepository;
import org.hibernate.id.uuid.Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
@ExtendWith(MockitoExtension.class)
public class GenreServiceTests {

    @InjectMocks
    GenreServiceImpl genreService;

    @Mock
    GenreRepository mockRepository;

    @Test
    public void findAll_Should_CallRepository() {
        //Arrange
        Pageable pageable = PageRequest.of(1,5);
        Mockito.when(mockRepository.findAll(Mockito.any(Pageable.class))).thenReturn(null);

        //Act
        genreService.findAll(pageable);

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void findById_Should_ReturnGenreWhenValidId() {
        //Arrange
        Genre mockGenre = Helpers.createMockGenre();
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(mockGenre);


        //Act
        Genre result = genreService.findById(Mockito.anyInt());

        //Assert
        Assertions.assertEquals(mockGenre, result);
    }
}
