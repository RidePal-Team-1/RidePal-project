package com.example.ridepal.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TrackResult {
    private Set<Track> trackSet;
    private int totalDuration;
}
