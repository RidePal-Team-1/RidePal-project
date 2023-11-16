package com.example.ridepal.controllers.rest;

import com.example.ridepal.filters.UserSortField;
import com.example.ridepal.models.User;
import com.example.ridepal.services.contracts.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

       return userService.findById(id);
    }

}
