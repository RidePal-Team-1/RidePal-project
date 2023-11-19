package com.example.ridepal.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"__type", "bbox", "name", "address","confidence","entityType","geocodePoints",
        "matchCodes"})
public class BingLocation {
    private BingPoint point;
}
