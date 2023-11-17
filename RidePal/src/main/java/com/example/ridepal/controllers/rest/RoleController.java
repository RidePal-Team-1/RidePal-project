package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.filters.enums.UserSortField;
import com.example.ridepal.mappers.RoleMapper;
import com.example.ridepal.models.Role;
import com.example.ridepal.models.dtos.RoleDto;
import com.example.ridepal.services.contracts.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    private final RoleMapper mapper;


    public RoleController(RoleService roleService, RoleMapper mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<Role> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int sizePerPage,
                              @RequestParam(defaultValue = "ID") UserSortField sortField,
                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page, sizePerPage,sortDirection,sortField.getDatabaseFieldName());
        return roleService.findAll(pageable);
    }

    @PostMapping
    public void create(@Valid @RequestBody RoleDto dto) {
            Role role = mapper.fromDto(dto);
            roleService.create(role);
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody RoleDto dto, @PathVariable int id) {
        try {
            Role role = mapper.fromDto(dto, id);
            roleService.update(role);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            roleService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
