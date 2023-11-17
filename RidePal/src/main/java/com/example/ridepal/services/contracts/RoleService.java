package com.example.ridepal.services.contracts;

import com.example.ridepal.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void create(Role role);

    void update(Role role);

    void delete(int id);
}
