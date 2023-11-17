package com.example.ridepal.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class RoleDto {

    @NotEmpty(message = "Role name cannot be empty")
    @Size(max = 15, message = "Role name cannot exceed 15 characters!")
    private String name;
}
