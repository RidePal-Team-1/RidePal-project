package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "total_playtime")
    private int playtime;

    @Column(name = "rank")
    private double rank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlists_tracks",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> trackSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlists_tags",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> genres = new HashSet<>();
}
