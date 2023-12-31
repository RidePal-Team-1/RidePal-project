package com.example.ridepal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.example.ridepal.repositories")
@EntityScan("com.example.ridepal.models")
@EnableScheduling
public class RidePalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RidePalApplication.class, args);
    }
}
