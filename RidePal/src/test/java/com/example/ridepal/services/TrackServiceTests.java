package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.TrackSortField;
import com.example.ridepal.models.Track;
import com.example.ridepal.repositories.TrackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTests {
    @Mock
    TrackRepository trackRepository;

    @InjectMocks
    TrackServiceImpl trackService;

    @Test
    public void findAll_Should_callRepository(){
        Sort sort = Sort.by(Sort.Direction.ASC, TrackSortField.RANK.getDatabaseFieldName());

        trackService.findAll(sort);

        Mockito.verify(trackRepository, Mockito.times(1)).findAll(sort);
    }

    @Test
    public void findById_Should_callRepository(){
        Track track = new Track();
        track.setId(1);

        Mockito.when(trackRepository.findById(1)).thenReturn(track);

        trackService.findById(1);

        Mockito.verify(trackRepository, Mockito.times(2)).findById(1);
    }

    @Test
    public void findById_Should_returnTrack_When_exists(){
        Track track = new Track();
        track.setId(1);

        Mockito.when(trackRepository.findById(1)).thenReturn(track);

        Assertions.assertEquals(track,trackService.findById(1));
    }

    @Test
    public void findById_Should_throwException_When_trackDoesNotExist(){
        Track track = new Track();

        Mockito.when(trackRepository.findById((int)track.getId()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> trackRepository.findById((int)track.getId()));
    }
}
