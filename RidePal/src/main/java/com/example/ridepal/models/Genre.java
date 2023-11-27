package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "genre_id")
    private long id;

    @Column(name = "genre_name")
    private String name;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Override
    public String toString() {
        return name;
    }
}

