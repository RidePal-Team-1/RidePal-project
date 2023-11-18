package com.example.ridepal.deezer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeezerTrackResponse {
    private List<DeezerTrack> data;
    private String checksum;
    private int total;
    private String next;
}