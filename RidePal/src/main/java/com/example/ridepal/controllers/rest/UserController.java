package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.User;
import com.example.ridepal.models.filters.UserCriteria;
import com.example.ridepal.services.contracts.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> findAll(UserCriteria criteria) {
        return userService.findAll(criteria);
    }
}
