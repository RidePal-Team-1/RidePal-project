package com.example.ridepal.services;

import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Role;
import com.example.ridepal.repositories.RoleRepository;
import com.example.ridepal.services.contracts.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public void create(Role role) {
        checkRoleUniqueness(role);
        roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        checkRoleUniqueness(role);
        roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
       Role role =  roleRepository.findById(id);
       if (role == null) {
           throw new EntityNotFoundException("Role", id);
       }
       roleRepository.delete(role);
    }

    private void checkRoleUniqueness(Role role) {
        Role repositoryRole = roleRepository.findByName(role.getName());
        if (repositoryRole != null) {
            throw new DuplicateEntityException("Role", "name", role.getName());
        }
    }

}
