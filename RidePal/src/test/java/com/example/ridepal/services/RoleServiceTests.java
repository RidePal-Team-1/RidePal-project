package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Role;
import com.example.ridepal.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    @Test
    public void findAll_Should_CallRepository() {
        //Arrange
        Pageable pageable = PageRequest.of(1, 5);

        //Act
        roleService.findAll(pageable);

        //Assert
        Mockito.verify(roleRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void create_Should_Throw_WhenRoleNameExists() {
        //Arrange
        Role mockRole = Helpers.createMockAdminRole();
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(mockRole);

        //Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> roleService.create(mockRole));
    }

    @Test
    public void create_Should_CallRepository() {
        //Arrange
        Role mockRole = Helpers.createMockUserRole();

        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(null);

        //Act
        roleService.create(mockRole);

        //Assert
        Mockito.verify(roleRepository, Mockito.times(1)).save(mockRole);
    }

    @Test
    public void update_Should_Throw_WhenRoleNameExists() {
        //Arrange
        Role mockRole = Helpers.createMockAdminRole();
        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(mockRole);

        //Act & Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> roleService.update(mockRole));
    }

    @Test
    public void update_Should_CallRepository() {
        //Arrange
        Role mockRole = Helpers.createMockUserRole();

        Mockito.when(roleRepository.findByName(Mockito.anyString())).thenReturn(null);

        //Act
        roleService.update(mockRole);

        //Assert
        Mockito.verify(roleRepository, Mockito.times(1)).save(mockRole);
    }

    @Test
    public void delete_Should_Throw_WhenRoleNotFound() {
        //Arrange
        Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(null);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> roleService.delete(Mockito.anyInt()));
    }

    @Test
    public void delete_Should_CallRepository() {
        //Arrange
        Role mockRole = Helpers.createMockAdminRole();
        Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(mockRole);

        //Act
        roleService.delete(Mockito.anyInt());

        //Assert
        Mockito.verify(roleRepository, Mockito.times(1)).delete(mockRole);
    }
}
