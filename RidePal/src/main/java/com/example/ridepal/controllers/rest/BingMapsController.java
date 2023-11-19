package com.example.ridepal.controllers.rest;

import com.example.ridepal.maps.BingLocationResponse;
import com.example.ridepal.services.contracts.BingMapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maps")
public class BingMapsController {

    private final BingMapService bingMapsService;


    public BingMapsController(BingMapService bingMapsService) {
        this.bingMapsService = bingMapsService;
    }

   @GetMapping
    public void getLocation(@RequestParam(required = true) String startPoint,
                                                  @RequestParam(required = true) String endPoint){
      bingMapsService.getLocations(startPoint, endPoint);
    }
}
