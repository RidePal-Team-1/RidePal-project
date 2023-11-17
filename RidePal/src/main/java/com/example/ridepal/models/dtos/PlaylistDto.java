package com.example.ridepal.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PlaylistDto {

    public static final String PLAYLIST_TITLE_LENGTH_ERROR = "Title must be between 4 and 40 symbols.";
    @NotEmpty
    @Size(min = 4, max = 40, message = PLAYLIST_TITLE_LENGTH_ERROR)
    private String title;

    public PlaylistDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
