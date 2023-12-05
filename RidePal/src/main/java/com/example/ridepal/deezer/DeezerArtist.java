package com.example.ridepal.deezer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerArtist {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("tracklist")
    private String trackList;

    @JsonProperty("picture_medium")
    private String photoUrl;

}
