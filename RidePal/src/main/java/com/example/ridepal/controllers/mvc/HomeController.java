package com.example.ridepal.controllers.mvc;

import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.PlaylistRepository;
import com.example.ridepal.repositories.UserRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class HomeController {


    private final UserService userService;

    private final PlaylistRepository playlistRepository;

    private final GenreRepository genreRepository;

    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserService userService, PlaylistRepository playlistRepository, GenreRepository genreRepository, UserRepository userRepository) {
        this.userService = userService;
        this.playlistRepository = playlistRepository;
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String landing(){
        return "LandingPage";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        extractUserFromProvider(model, authentication);
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


    @ModelAttribute("getTopPlaylists")
    private List<Playlist> getTopPlaylists() {
        return playlistRepository.getTopPlaylists();
    }

    @ModelAttribute("getPlaylistsCount")
    private int getPlaylistsCount() {
        return playlistRepository.getPlaylistsCount();
    }

    @ModelAttribute("getGenresCount")
    private int getGenresCount() {
        return genreRepository.getGenresCount();
    }

    @ModelAttribute("getUsersCount")
    private int getUsersCount() {
        return userRepository.getUsersCount();
    }
    @ModelAttribute("genres")
    private List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @ModelAttribute("getPlaylistsPerGenre")
    private Map<Long, Integer> getPlaylistsPerGenre() {
        Map<Long, Integer> genres = new HashMap<>();
        for (Genre genre : genreRepository.findAll()) {
            genres.put(genre.getId(), playlistRepository.getPlaylistsCountByGenre(genre.getId()));
        }
        return genres;
    }
}
