package com.example.ridepal.services;

import com.example.ridepal.Helpers;
import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;

import static com.example.ridepal.filters.specifications.UserSpecifications.*;
import static com.example.ridepal.filters.specifications.UserSpecifications.email;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository mockRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void findById_Should_Throw_WhenUserDoesNotExist() {

        //Arrange
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(null);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(2));
    }

    @Test
    public void findById_Should_ReturnUser_WhenValidId() {
        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(mockUser);

        //Act
        User result = userService.findById(2);

        //Assert
        Assertions.assertEquals(result, mockUser);
    }

    @Test
    public void findById_Should_CallRepository() {

        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(mockUser);

        //Act
        userService.findById(Mockito.anyInt());

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    public void findByUsername_Should_Throw_WhenUserDoesNotExist() {

        //Arrange
        Mockito.when(mockRepository.findByUsername(Mockito.anyString())).thenReturn(null);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(Mockito.anyString()));
    }

    @Test
    public void findByUsername_Should_ReturnUser_WhenValidUsername() {
        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        //Act
        User result = userService.findByUsername("mockUsername");

        //Assert
        Assertions.assertEquals(result, mockUser);
    }

    @Test
    public void findByUsername_Should_CallRepository() {

        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByUsername(Mockito.anyString())).thenReturn(mockUser);

        //Act
        userService.findByUsername(Mockito.anyString());

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findByUsername(Mockito.anyString());
    }

    @Test
    public void findByEmail_Should_Throw_WhenUserDoesNotExist() {

        //Arrange
        Mockito.when(mockRepository.findByEmail(Mockito.anyString())).thenReturn(null);

        //Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByEmail("mockEmail"));
    }

    @Test
    public void findByEmail_Should_ReturnUser_WhenValidEmail() {
        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByEmail(Mockito.anyString())).thenReturn(mockUser);

        //Act
        User result = userService.findByEmail("mockEmail");

        //Assert
        Assertions.assertEquals(result, mockUser);
    }

    @Test
    public void findByEmail_Should_CallRepository() {

        //Arrange
        User mockUser = Helpers.createMockUser();
        Mockito.when(mockRepository.findByEmail(Mockito.anyString())).thenReturn(mockUser);

        //Act
        userService.findByEmail("mockEmail");

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findByEmail("mockEmail");
    }



//    @Test
//    public void create_Should_Throw_WhenUsernameIsDuplicated() {
//        //Arrange
//        User mockUser = Helpers.createMockUser();
//        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(mockUser);
//
//        //Act & Assert
//        Assertions.assertThrows(DuplicateEntityException.class, () -> userService.create(mockUser));
//    }


//    @Test
//    public void create_Should_Throw_WhenEmailIsDuplicated() {
//        //Arrange
//        User mockUser = Helpers.createMockUser();
//        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null).thenReturn(mockUser);
//
//        //Act & Assert
//        Assertions.assertThrows(DuplicateEntityException.class, () -> userService.create(mockUser));
//    }
    @Test
    public void create_Should_CallRepository() {
        //Arrange
        User mockUser = Helpers.createMockUser();

        Mockito.when(mockRepository.findByUsername(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockRepository.save(mockUser)).thenReturn(mockUser);
        //Act
        userService.create(mockUser);

        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
    }

//    @Test
//    public void update_Should_CallRepository() {
//        //Arrange
//        User mockUser = Helpers.createMockUser();
//
//        //Act
//        userService.update(mockUser);
//
//        //Assert
//        Mockito.verify(mockRepository, Mockito.times(1)).save(mockUser);
//    }
//
//    @Test
//    public void delete_Should_CallRepository() {
//        //Arrange
//        User mockUser = Helpers.createMockUser();
//        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(mockUser);
//        Mockito.doNothing().when(mockRepository).delete(mockUser);
//
//        //Act
//        userService.delete(Mockito.anyInt());
//
//        //Assert
//        Mockito.verify(mockRepository, Mockito.times(1)).delete(mockUser);
//    }

//    @Test
//    public void delete_Should_Throw_WhenUserNotFound() {
//
//        //Arrange
//
//        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(null);
//
//        //Act & Assert
//        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.delete(2));
//    }

}
