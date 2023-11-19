package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.UserSortField;
import com.example.ridepal.mappers.UserMapper;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.UserDto;
import com.example.ridepal.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<User> findAll(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int sizePerPage,
                              @RequestParam(defaultValue = "ID") UserSortField sortField,
                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection, sortField.getDatabaseFieldName());
        return userService.findAll(username, firstName, lastName, email, pageable);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        try {
            return userService.findById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping
    public User create(@Valid @RequestBody UserDto dto) {
       User user = mapper.fromDto(dto);
       return userService.create(user);
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody UserDto dto, @PathVariable int id) {
        try{
            User user = mapper.fromDto(dto, id);
            userService.update(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try{
            userService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
