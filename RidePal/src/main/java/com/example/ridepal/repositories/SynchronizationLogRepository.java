package com.example.ridepal.repositories;

import com.example.ridepal.models.SynchronizationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SynchronizationLogRepository extends JpaRepository<SynchronizationLog, Integer> {

    @Query(value = "select * from synchronization_logs order by synchronization_logs.log_id DESC limit 1", nativeQuery = true)
    SynchronizationLog findLastLog();
}
