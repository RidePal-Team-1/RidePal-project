package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private int id;

    @Column(name = "album_name")
    private String name;

    @Column(name = "album_tracklist_url")
    private String url;
}
