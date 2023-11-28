package com.example.ridepal.repositories;

import com.example.ridepal.models.SynchronizationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SynchronizationConfigRepository extends JpaRepository<SynchronizationConfig, Integer> {

    SynchronizationConfig findById(long id);
}
