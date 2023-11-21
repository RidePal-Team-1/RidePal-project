package com.example.ridepal.models.dtos;

import com.example.ridepal.models.Genre;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class PlaylistDto {

    public static final String PLAYLIST_TITLE_LENGTH_ERROR = "Title must be between 4 and 40 symbols.";
    @NotEmpty
    @Size(min = 4, max = 40, message = PLAYLIST_TITLE_LENGTH_ERROR)
    private String title;

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    @NotNull
    private Map<String, Double> genres;

    private boolean useTopTracks;

    private boolean tracksFromSameArtist;
}
