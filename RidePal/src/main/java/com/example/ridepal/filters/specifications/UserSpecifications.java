package com.example.ridepal.filters.specifications;

import com.example.ridepal.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    private UserSpecifications() {

    }

    public static Specification<User> username(String username) {
        return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> firstName(String firstName) {
        return (root, query, builder) -> builder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<User> lastName(String lastName) {
        return (root, query, builder) -> builder.like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<User> email(String email) {
        return (root, query, builder) -> builder.like(root.get("email"), "%" + email + "%");
    }
}
