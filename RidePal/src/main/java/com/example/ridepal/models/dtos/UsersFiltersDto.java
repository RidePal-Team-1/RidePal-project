package com.example.ridepal.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersFiltersDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
