package com.example.ridepal.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistFiltersDto {

    private String title;
    private String genre;
    private String minDuration;
    private String maxDuration;
}
