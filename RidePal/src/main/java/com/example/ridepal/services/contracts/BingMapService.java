package com.example.ridepal.services.contracts;

import com.example.ridepal.maps.BingLocationResponse;

public interface BingMapService {
    BingLocationResponse getLocation(String locationQuery);
}
