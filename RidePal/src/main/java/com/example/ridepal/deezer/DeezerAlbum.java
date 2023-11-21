package com.example.ridepal.deezer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerAlbum {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("tracklist")
    private String trackList;
}
//    private String cover;
//    private String cover_small;
//    private String cover_medium;
//    private String cover_big;
//    private String cover_xl;