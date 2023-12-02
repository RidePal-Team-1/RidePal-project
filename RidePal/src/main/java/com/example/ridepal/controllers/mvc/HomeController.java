package com.example.ridepal.controllers.mvc;

import com.example.ridepal.helpers.AuthenticationHelper;
import com.example.ridepal.models.Genre;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.SynchronizationLog;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.SynchronizationConfigDto;
import com.example.ridepal.repositories.*;
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

    private final PlaylistRepository playlistRepository;

    private final GenreRepository genreRepository;

    private final UserRepository userRepository;

    private final SynchronizationConfigRepository synchronizationConfigRepository;

    private final SynchronizationLogRepository synchronizationLogRepository;

    @Autowired
    public HomeController(PlaylistRepository playlistRepository, GenreRepository genreRepository,
                          UserRepository userRepository, SynchronizationConfigRepository synchronizationConfigRepository,
                          SynchronizationLogRepository synchronizationLogRepository) {
        this.playlistRepository = playlistRepository;
        this.synchronizationLogRepository = synchronizationLogRepository;
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
        this.synchronizationConfigRepository = synchronizationConfigRepository;
    }

    @GetMapping
    public String landing(){
        return "LandingPage";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        model.addAttribute("user", user);
        model.addAttribute("genreSync", new SynchronizationConfigDto());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
    return "about";
    }

    @ModelAttribute("lastLog")
    private SynchronizationLog getLastLog() {
        return synchronizationLogRepository.findLastLog();
    }

    @ModelAttribute("currentInterval")
    private long getCurrentInterval() {
        return synchronizationConfigRepository.findById(1).getSynchronizationInterval();
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
