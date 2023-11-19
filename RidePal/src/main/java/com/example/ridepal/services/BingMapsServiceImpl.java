package com.example.ridepal.services;

import com.example.ridepal.maps.BingConfig;
import com.example.ridepal.maps.BingLocation;
import com.example.ridepal.maps.BingLocationResponse;
import com.example.ridepal.services.contracts.BingMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class BingMapsServiceImpl implements BingMapService {

    private final BingConfig bingConfig;

    private final RestTemplate restTemplate;
    public BingLocationResponse getLocation(String locationQuery){
        String endPoint = bingConfig.getBaseUrl()+"/REST/v1/Locations/";
        String url = String.format("%s%s?key=%s", endPoint, locationQuery, bingConfig.getApiKey());
        BingLocationResponse locationResponse = restTemplate.getForObject(url, BingLocationResponse.class);
        return locationResponse;
    }
}
