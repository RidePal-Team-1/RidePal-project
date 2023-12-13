package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.InvalidGenreSynchronizationInputException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.RoleRepository;
import com.example.ridepal.repositories.SynchronizationConfigRepository;
import org.hibernate.id.uuid.Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ScheduledExecutorService;

@ExtendWith(MockitoExtension.class)
public class SynrchonizationServiceTests {


    @Mock
    SynchronizationConfigRepository synchronizationConfigRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    SynchronizationConfigServiceImpl mockService;

    @Mock
     ScheduledExecutorService scheduler;

    @Test
    public void updateInterval_ShouldCallRepository() {
        //Arrange
        User mockUser = Helpers.createMockUser();
        mockUser.getRoles().add(Helpers.createMockAdminRole());
        SynchronizationConfig mockConfig = Helpers.createMockSynchronizationConfig();
        Mockito.when(synchronizationConfigRepository.findById(1)).thenReturn(mockConfig);
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(Helpers.createMockAdminRole());
        Mockito.doNothing().when(scheduler).shutdown();

        //Act
        mockService.updateInterval(100, mockUser);


        //Assert
        Mockito.verify(synchronizationConfigRepository, Mockito.times(1)).save(mockConfig);
    }

    @Test
    public void updateInterval_Should_ThrowWhenIntervalNotInRange() {

        //Arrange
        User mockUser = Helpers.createMockUser();

        //Act & Assert
        Assertions.assertThrows(InvalidGenreSynchronizationInputException.class, () -> mockService.updateInterval(1500, mockUser));
    }

    @Test
    public void updateInterval_Should_ThrowWhenUserNotAdmin() {
        //Arrange
        User mockUser = Helpers.createMockUser();

        //Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> mockService.updateInterval(100, mockUser));

    }
}
