package com.example.ridepal.mappers;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.UserDto;
import com.example.ridepal.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User fromDto(UserDto dto, int id) {
        User repositoryUser = userRepository.findById(id);

        if (repositoryUser == null) {
            throw new EntityNotFoundException("User", id);
        }
        User user = fromDto(dto);

        user.setId(id);
        return user;
    }

    public User fromDto(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFirst_name(dto.getFirst_name());
        user.setLast_name(dto.getLast_name());
        user.setProfile_picture(dto.getProfile_picture());
        return user;
    }


}
