package com.example.ridepal.services;

import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.filters.enums.Provider;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.RoleRepository;
import com.example.ridepal.repositories.UserRepository;
import com.example.ridepal.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.ridepal.filters.specifications.UserSpecifications.*;

@Service
public class UserServiceImpl implements UserService {

    public static final String UNAUTHORIZED_MSG = "You are not authorized to perform this operation!";
    public static final int DELETED_USER_ID = 13;
    public static final String ADMIN_ROLE = "ADMIN";
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PlaylistRepository playlistRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.playlistRepository = playlistRepository;
    }


    @Override
    public Page<User> findAll(String usernameFilter, String firstNameFilter, String lastNameFilter, String emailFilter, Pageable pageable) {
        Specification<User> filters = Specification.where(StringUtils.isEmptyOrWhitespace(usernameFilter) ? null : username(usernameFilter))
                .and(StringUtils.isEmptyOrWhitespace(firstNameFilter) ? null : firstName(firstNameFilter))
                .and(StringUtils.isEmptyOrWhitespace(lastNameFilter) ? null : lastName(lastNameFilter))
                .and(StringUtils.isEmptyOrWhitespace(emailFilter) ? null : email(emailFilter));
        return userRepository.findAll(filters, pageable);
    }

    @Override
    public User findById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User", id);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User", "username", username);
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User", "email", email);
        }
        return user;
    }


    @Override
    public User create(User user) {
        checkUsernameUniqueness(user);
        checkEmailUniqueness(user);
        return userRepository.save(user);
    }

    @Override
    public void update(User userToUpdate, User authenticatedUser) {
        if (userToUpdate.getId() != authenticatedUser.getId()) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_MSG);
        }
        checkUsernameUniqueness(userToUpdate);
        checkEmailUniqueness(userToUpdate);
        userRepository.save(userToUpdate);
    }

    @Override
    public void delete(int id, User authenticatedUser) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User", id);
        }
        if (id != authenticatedUser.getId() &&
                !authenticatedUser.getRoles().contains(roleRepository.findByName(ADMIN_ROLE))) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_MSG);
        }

        User deletedUser = userRepository.findById(DELETED_USER_ID);
        playlistRepository.transferPlaylistsToDeletedUser(deletedUser, user);
        userRepository.delete(user);
    }

    private void checkUsernameUniqueness(User user) {
        List<User> repositoryUsers = userRepository.findListByUsername(user.getUsername());
        if(user.getProvider().toString().equals("GOOGLE")){
            return;
        }
        if (repositoryUsers.size() > 1) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
    }

    private void checkEmailUniqueness(User user) {
        List<User> repositoryUsers = userRepository.findListByEmail(user.getEmail());
        if (repositoryUsers.size() > 1) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
    }

    public void processOAuthPostLogin(String email) {
        User existUser = userRepository.findByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProvider(Provider.GOOGLE);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("USER"));
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }
    }

    public List<Playlist> getUserPlaylists(int id){
        return playlistRepository.getUserPlaylists(id);
    }
}
