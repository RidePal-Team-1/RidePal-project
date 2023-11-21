package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Column(name = "track_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "playtime")
    private int playtime;

    @Column(name = "rank")
    private long rank;

    @Column(name = "preview_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
