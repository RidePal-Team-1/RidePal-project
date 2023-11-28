package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "synchronization_configs")
@Getter
@Setter
@NoArgsConstructor
public class SynchronizationConfig {

    @Id
    @Column(name = "config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "synchronization_interval")
    private long synchronizationInterval;

}
