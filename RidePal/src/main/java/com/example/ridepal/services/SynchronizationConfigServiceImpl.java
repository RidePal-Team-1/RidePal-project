package com.example.ridepal.services;

import com.example.ridepal.exceptions.GenreSynchronizationFailureException;
import com.example.ridepal.exceptions.InvalidGenreSynchronizationInputException;
import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.models.SynchronizationLog;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.RoleRepository;
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
    public static final String UNAUTHORIZED_MSG = "User is not authorized to perform this operation!";
    public static final String ADMIN_ROLE = "ADMIN";
    private final SynchronizationConfigRepository synchronizationConfigRepository;

    private final SynchronizationLogRepository synchronizationLogRepository;

    private ScheduledExecutorService scheduler;

    private final DeezerService deezerService;

    private final RoleRepository roleRepository;

    @Autowired
    public SynchronizationConfigServiceImpl(SynchronizationConfigRepository synchronizationConfigRepository,
                                            SynchronizationLogRepository synchronizationLogRepository,
                                            DeezerService deezerService,
                                            ScheduledExecutorService scheduler,
                                            RoleRepository roleRepository) {
        this.synchronizationConfigRepository = synchronizationConfigRepository;
        this.synchronizationLogRepository = synchronizationLogRepository;
        this.deezerService = deezerService;
        this.scheduler = scheduler;
        this.roleRepository = roleRepository;
    }

    @Override
    public void updateInterval(long interval, User user) {
        if (interval > 1440) {
            throw new InvalidGenreSynchronizationInputException(INVALID_TIME);
        }
        if (!user.getRoles().contains(roleRepository.findByName(ADMIN_ROLE))) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_MSG);
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
        SynchronizationConfig config = synchronizationConfigRepository.findById(CONFIG_DATABASE_ID);
        return config.getSynchronizationInterval();
    }

//    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
   @Override
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
