package com.example.ridepal.controllers.rest;

import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.services.contracts.SynchronizationConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
@Tag(name = "Genre Synchronization")
public class SynchronizationConfigController {

    private final SynchronizationConfigService synchronizationConfigService;
    public SynchronizationConfigController(SynchronizationConfigService synchronizationConfigService) {
        this. synchronizationConfigService = synchronizationConfigService;
    }

    @PutMapping
    @Operation(summary = "Change genre synchronization interval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interval changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SynchronizationConfig.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)})
    public void updateInterval(@RequestParam long interval) {
        synchronizationConfigService.updateInterval(interval);
    }

}
