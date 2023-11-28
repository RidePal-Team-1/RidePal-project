package com.example.ridepal.repositories;

import com.example.ridepal.models.SynchronizationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SynchronizationLogRepository extends JpaRepository<SynchronizationLog, Integer> {

    @Query(value = "select SynchronizationLog from SynchronizationLog order by id DESC limit 1")
    SynchronizationLog findLastLog();
}
