package com.example.ridepal.controllers.mvc;

import com.example.ridepal.models.User;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("")
public class HomeController {


    private final UserService userService;

    private final PlaylistRepository playlistRepository;

    @Autowired
    public HomeController(UserService userService, PlaylistRepository playlistRepository) {
        this.userService = userService;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    public String landing(){
        return "LandingPage";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        extractUserFromProvider(model, authentication);
        model.addAttribute("playlists", playlistRepository.getTopPlaylists());
        return "home";
    }



    @GetMapping("/about")
    public String about() {
    return "about";
    }


    private void extractUserFromProvider(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication instanceof OAuth2AuthenticationToken) {
                // OAuth2 authentication
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                OAuth2User oauthUser = oauthToken.getPrincipal();
                String email = oauthUser.getAttribute("email"); // Replace with the actual attribute name

                User user = userService.findByEmail(email);
                model.addAttribute("user", user);
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // Standard login authentication
                String email = authentication.getName();

                User user = userService.findByEmail(email);
                model.addAttribute("user", user);
            }
        }
    }
}
