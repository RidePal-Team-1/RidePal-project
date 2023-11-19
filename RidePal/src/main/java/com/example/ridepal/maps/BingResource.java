package com.example.ridepal.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingResource {
    @JsonProperty("name")
    private String name;

    @JsonProperty("point")
    private BingPoint point;
}
