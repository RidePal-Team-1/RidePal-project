package com.example.ridepal.services.contracts;

import com.example.ridepal.maps.BingLocationResponse;

import java.util.List;

public interface BingMapService {
    double[] getLocations(String startPoint, String endPoint);
}
