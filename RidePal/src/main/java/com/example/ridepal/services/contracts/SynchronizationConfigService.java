package com.example.ridepal.services.contracts;

import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.models.User;

public interface SynchronizationConfigService {

    void updateInterval(long interval, User user);

    void syncJob();

}
