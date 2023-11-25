package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "albums")
public class Album {

    @Id
    @Column(name = "album_id")
    private long id;

    @Column(name = "album_name")
    private String name;

    @Column(name = "album_tracklist_url")
    private String url;

    @Column(name = "photoUrl")
    private String photoUrl;
}
