package com.example.ridepal.services.contracts;

import com.example.ridepal.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findAll(String username, String firstName, String lastName, String email, Pageable pageable);

    User findById(int id);

    User create(User user);

    void update(User user);

    void delete(int id);

    void processOAuthPostLogin(String username);

}
