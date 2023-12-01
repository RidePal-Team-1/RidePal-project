package com.example.ridepal.controllers.mvc;

import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.helpers.AuthenticationHelper;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.SynchronizationConfigDto;
import com.example.ridepal.services.contracts.SynchronizationConfigService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sync")
public class SynchronizationConfigMvcController {

    private final SynchronizationConfigService synchronizationConfigService;
    public SynchronizationConfigMvcController(SynchronizationConfigService synchronizationConfigService) {
        this.synchronizationConfigService = synchronizationConfigService;
    }
    @GetMapping()
    public String syncGenres(@Valid @ModelAttribute SynchronizationConfigDto dto, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            synchronizationConfigService.updateInterval(dto.getInterval(), user);
            return "redirect:/home";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", e.getMessage());
            return "ErrorView";
        }
    }
}
