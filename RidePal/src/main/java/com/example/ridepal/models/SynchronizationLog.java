package com.example.ridepal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "synchronization_logs")
@Getter
@Setter
@NoArgsConstructor
public class SynchronizationLog {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "status")
    private String status;

    @Column(name = "exception_details", columnDefinition = "TEXT")
    private String exceptionDetails;
}
