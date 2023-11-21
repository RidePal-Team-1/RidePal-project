package com.example.ridepal.deezer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerTrack {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("rank")
    private int rank;
    @JsonProperty("preview")
    private String preview;
    @JsonProperty("artist")
    private DeezerArtist artist;
    @JsonProperty("album")
    private DeezerAlbum album;
}
//    private String md5_image;