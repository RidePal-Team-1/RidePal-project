package com.example.ridepal.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingAddress {
    @JsonProperty("countryRegion")
    private String countryRegion;

    @JsonProperty("formattedAddress")
    private String formattedAddress;
}
