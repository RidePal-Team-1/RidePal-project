package com.example.ridepal.controllers.rest;

import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.helpers.AuthenticationHelper;
import com.example.ridepal.models.SynchronizationConfig;
import com.example.ridepal.models.User;
import com.example.ridepal.services.contracts.SynchronizationConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public void updateInterval(@RequestParam long interval, Authentication authentication) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            synchronizationConfigService.updateInterval(interval, user);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
