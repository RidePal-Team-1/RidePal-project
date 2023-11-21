package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @Column(name = "artist_id")
    private long id;

    @Column(name = "artist_name")
    private String name;

    @Column(name = "artist_tracklist_url")
    private String trackListUrl;
}
