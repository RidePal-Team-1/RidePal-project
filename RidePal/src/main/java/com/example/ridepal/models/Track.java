package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "playlists")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private int id;

    @Column(name = "artist")
    private String artist;

    @Column(name = "track_title")
    private String title;

    @Column(name = "album")
    private String album;

    @Column(name = "playtime")
    private int playtime;

    @Column(name = "rank")
    private double rank;

    @Column(name = "preview_url")
    private String url;
}
