package com.example.ridepal.mappers;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.dtos.RoleDto;
import com.example.ridepal.repositories.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final RoleRepository roleRepository;


    public RoleMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role fromDto(RoleDto dto, int id) {
        Role repositoryRole = roleRepository.findById(id);
        if (repositoryRole == null) {
            throw new EntityNotFoundException("Role", id);
        }
        Role role = fromDto(dto);
        role.setId(id);
        return role;
    }
    public Role fromDto(RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        return role;
    }
}
