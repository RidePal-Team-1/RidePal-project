package com.example.ridepal.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateDto {
    public static final String PLAYLIST_TITLE_LENGTH_ERROR = "Title must be between 4 and 40 symbols.";

    @NotEmpty
    @Size(min = 4, max = 40, message = PLAYLIST_TITLE_LENGTH_ERROR)
    private String title;
}
