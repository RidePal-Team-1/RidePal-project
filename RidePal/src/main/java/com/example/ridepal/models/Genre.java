package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "genre_id")
    private long id;

    @Column(name = "genre_name")
    private String name;
}
