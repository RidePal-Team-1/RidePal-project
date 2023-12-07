package com.example.ridepal.controllers.mvc;

import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.filters.enums.UserSortField;
import com.example.ridepal.helpers.AuthenticationHelper;
import com.example.ridepal.mappers.UserMapper;
import com.example.ridepal.models.Genre;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.models.dtos.UserDto;
import com.example.ridepal.models.dtos.UsersFiltersDto;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    public static final int PAGE = 0;
    public static final int PAGE_SIZE = 10;
    private final UserService userService;

    private final UserMapper userMapper;

    private final GenreRepository genreRepository;

    private final PlaylistRepository playlistRepository;


    @Autowired
    public UserMvcController(UserService userService, UserMapper userMapper, GenreRepository genreRepository, PlaylistRepository playlistRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.genreRepository = genreRepository;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable  int id, Model model, Authentication authentication) {
        User authenticatedUser = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            User userRepository = userService.findById(id);
            UserDto user = userMapper.toDto(userRepository);
            model.addAttribute("userDto", user);
            model.addAttribute("userRepository", userRepository);
            model.addAttribute("authenticatedUser", authenticatedUser);
            model.addAttribute("user", authenticatedUser);
            model.addAttribute("id", id);
            return "UserView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("user", authenticatedUser);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "ErrorView";
        }
    }

    @GetMapping
    public String allUsersView(@ModelAttribute("filterOptions") UsersFiltersDto usersFiltersDto,
                               Model model, Authentication authentication,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int sizePerPage,
                               @RequestParam(defaultValue = "ID") UserSortField sortField,
                               @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection){
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        Page<User> users = userService.findAll(usersFiltersDto.getUsername(), usersFiltersDto.getFirstName(),
                usersFiltersDto.getLastName(), usersFiltersDto.getEmail(), pageable);

        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("userPages", pageNumbers);
        }
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("filterOptions", usersFiltersDto);
        return "UsersView";
    }



    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable int id, @Valid @ModelAttribute("userDto") UserDto dto, BindingResult bindingResult, Model model,
                             Authentication authentication) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRepository",userService.findById(id));
            model.addAttribute("authenticatedUser", user);
            return "redirect:/users/"+id;
        }

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password_error", "Passwords must match!");
            return "UserView";
        }
        try {
            User userToUpdate = userMapper.fromDto(dto, id);
            userService.update(userToUpdate, user);
            model.addAttribute("user", userToUpdate);
            model.addAttribute("authenticatedUser", user);
            return "redirect:/users/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "ErrorView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.CONFLICT.getReasonPhrase());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/picture")
    public String updatePlaylistPicture(@PathVariable int id,
                                        @RequestParam(value = "photoUrl", required = false) String photoUrl,
                                        Authentication authentication, Model model){
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            User userToUpdate = userService.findById(id);
            if (photoUrl != null && !photoUrl.isEmpty()) {
                // Update the photo URL in the UserDto object
                userToUpdate.setProfile_picture(photoUrl);
            }
            model.addAttribute("user", user);
            userService.update(userToUpdate, user);
            return "redirect:/users/" + id;
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "ErrorView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable int id, Model model, Authentication authentication) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            userService.delete(id, user);
            if (id == user.getId()) {
                return "redirect:/auth/logout";
            } else {
                return "redirect:/users";
            }
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "ErrorView";
        }
    }

    @ModelAttribute("getPlaylistsPerGenre")
    private Map<Long, Integer> getPlaylistsPerGenre() {
        Map<Long, Integer> genres = new HashMap<>();
        for (Genre genre : genreRepository.findAll()) {
            genres.put(genre.getId(), playlistRepository.getPlaylistsCountByGenre(genre.getId()));
        }
        return genres;
    }

    @ModelAttribute("playlistDto")
    private PlaylistDto prepareGeneratePlaylistDto() {
        PlaylistDto dto = new PlaylistDto();
        dto.setGenres(new HashMap<>());
        dto.getGenres().put("Rap/Hip Hop", 0.00);
        dto.getGenres().put("Rock", 0.00);
        dto.getGenres().put("Pop", 0.00);
        dto.getGenres().put("Jazz", 0.00);
        dto.getGenres().put("Electro", 0.00);
        dto.getGenres().put("Dance", 0.00);
        return dto;
    }

}
