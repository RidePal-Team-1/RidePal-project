package com.example.ridepal.mappers;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.Provider;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.UserDto;
import com.example.ridepal.repositories.RoleRepository;
import com.example.ridepal.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserMapper(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User fromDto(UserDto dto, int id) {
        User repositoryUser = userRepository.findById(id);
        if (repositoryUser == null) {
            throw new EntityNotFoundException("User", id);
        }
        User user = fromDto(dto);
        user.setRoles(repositoryUser.getRoles());
        user.setPlaylists(repositoryUser.getPlaylists());
        user.setId(id);
        return user;
    }

    public User fromDto(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setFirst_name(dto.getFirstName());
        user.setLast_name(dto.getLastName());
        user.setProfile_picture(dto.getProfilePicture());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        user.setProvider(Provider.LOCAL);
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setPasswordConfirm("");
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirst_name());
        userDto.setLastName(user.getLast_name());
        return userDto;
    }
}
