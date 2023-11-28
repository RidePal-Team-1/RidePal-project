package com.example.ridepal.services;

import com.example.ridepal.exceptions.GenreSynchronizationFailureException;
import com.example.ridepal.exceptions.InvalidGenreSynchronizationInputException;
import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.models.SynchronizationLog;
import com.example.ridepal.repositories.SynchronizationConfigRepository;
import com.example.ridepal.repositories.SynchronizationLogRepository;
import com.example.ridepal.services.contracts.DeezerService;
import com.example.ridepal.services.contracts.SynchronizationConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class SynchronizationConfigServiceImpl implements SynchronizationConfigService {

    public static final String INVALID_TIME = "The synchronization time cannot exceed 12 hours";
    public static final int CONFIG_DATABASE_ID = 1;
    private final SynchronizationConfigRepository synchronizationConfigRepository;

    private final SynchronizationLogRepository synchronizationLogRepository;

    private ScheduledExecutorService scheduler;

    private final DeezerService deezerService;

    @Autowired
    public SynchronizationConfigServiceImpl(SynchronizationConfigRepository synchronizationConfigRepository,
                                            SynchronizationLogRepository synchronizationLogRepository,
                                            DeezerService deezerService,
                                            ScheduledExecutorService scheduler) {
        this.synchronizationConfigRepository = synchronizationConfigRepository;
        this.synchronizationLogRepository = synchronizationLogRepository;
        this.deezerService = deezerService;
        this.scheduler = scheduler;
    }

    @Override
    public void updateInterval(long interval) {
        if (interval > 720) {
            throw new InvalidGenreSynchronizationInputException(INVALID_TIME);
        }
        SynchronizationConfig config = synchronizationConfigRepository.findById(CONFIG_DATABASE_ID);
        config.setSynchronizationInterval(interval);
        synchronizationConfigRepository.save(config);
        scheduler.shutdown();
        startScheduling();
    }

    @PostConstruct
    private void startScheduling() {
        long synchronizationInterval = getConfiguredInterval();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::syncJob, 0, synchronizationInterval, TimeUnit.MINUTES);
    }

    private long getConfiguredInterval() {
        // Retrieve synchronization interval from the database configuration
        SynchronizationConfig config = synchronizationConfigRepository.findById(1);
        return config.getSynchronizationInterval();
    }

//    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void syncJob() {
        SynchronizationLog log = new SynchronizationLog();
        log.setTimestamp(new Date());

        try {
            deezerService.getGenres();
            log.setStatus("success");
        } catch (GenreSynchronizationFailureException e) {
            // Log exception details
            log.setStatus("failure");
            log.setExceptionDetails(e.getMessage());
        }
            synchronizationLogRepository.save(log);
    }

}
