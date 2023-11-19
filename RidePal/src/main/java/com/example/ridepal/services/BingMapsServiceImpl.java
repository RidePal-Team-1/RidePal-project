package com.example.ridepal.services;

import com.example.ridepal.maps.BingConfig;
import com.example.ridepal.maps.BingLocation;
import com.example.ridepal.maps.BingLocationResponse;
import com.example.ridepal.services.contracts.BingMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BingMapsServiceImpl implements BingMapService {

    private final BingConfig bingConfig;

    private final RestTemplate restTemplate;
    public void getLocations(String startPoint, String endPoint){
        String location = bingConfig.getBaseUrl()+"/REST/v1/Locations/";

        String startLocationUrl = String.format("%s%s?key=%s",location, startPoint, bingConfig.getApiKey());
        String endLocationUrl = String.format("%s%s?key=%s",location, endPoint, bingConfig.getApiKey());

        BingLocationResponse startResponse = restTemplate.getForObject(startLocationUrl, BingLocationResponse.class);
        BingLocationResponse endResponse = restTemplate.getForObject(endLocationUrl, BingLocationResponse.class);

        List<Double> startCoordinates = startResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates();
        List<Double> endCoordinates = endResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates();

        String startLatitude = startCoordinates.get(0).toString()+',';
        String startLongitude = startCoordinates.get(1).toString();
        String endLatitude = endCoordinates.get(0).toString()+',';
        String endLongitude = endCoordinates.get(1).toString();

        getDurationAndDistance(startLatitude,startLongitude,endLatitude,endLongitude);
    }

    public void getDurationAndDistance(String startLatitude, String startLongitude,
                                       String endLatitude, String endLongitude){
        String url = String.format(
                "%s/REST/v1/Routes/DistanceMatrix?origins=%s%s&destinations=%s%s&travelMode=Driving&key=%s",
                bingConfig.getBaseUrl(),startLatitude,startLongitude,endLatitude,endLongitude,bingConfig.getApiKey());

        BingLocationResponse response = restTemplate.getForObject(url, BingLocationResponse.class);
        double travelDistance = response.getResourceSets().get(0).getResources().get(0).getResults().get(0).getTravelDistance();
        double travelDuration = response.getResourceSets().get(0).getResources().get(0).getResults().get(0).getTravelDuration();

        String success = "Great Success";
    }
}
