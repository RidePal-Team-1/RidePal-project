package com.example.ridepal.controllers.mvc;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistUpdateDto;
import com.example.ridepal.services.contracts.PlaylistService;
import com.example.ridepal.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlists")
public class PlaylistMvcController {
    private final PlaylistService playlistService;

    private final PlaylistMapper playlistMapper;

    private final UserService userService;

    public PlaylistMvcController(PlaylistService playlistService, PlaylistMapper playlistMapper, UserService userService) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public String playlistView(@PathVariable int id, Model model, Authentication authentication){
        extractUserFromProvider(model, authentication);
//        try{
        model.addAttribute("playlist", playlistService.getPlaylistById(id));
//        }catch (EntityNotFoundException){
//            return "redirect:"
//        }
        return "SinglePlaylistView";
    }

    @GetMapping("/{id}/update")
    public String updatePlaylist(@PathVariable int id,
                                 @Valid PlaylistUpdateDto playlistDto,
                                 BindingResult bindingResult,Model model, Authentication authentication){
        extractUserFromProvider(model, authentication);
        if(bindingResult.hasErrors()){
            return "redirect:/playlists/{id}";
        }
        try {
            model.addAttribute("id", id);
            Playlist playlist = playlistMapper.fromDto(playlistDto,id);
            playlistService.updatePlaylist(playlist);
            return "redirect:/playlists/{id}";
        }catch (EntityNotFoundException e){
            return "SinglePlaylistView"; //TODO to be changed after creation of ErrowView
        }
    }

    @PostMapping("/{id}/picture")
    public String updatePlaylistPicture(@PathVariable int id,
                                 @RequestParam(value = "photoUrl", required = false) String photoUrl,
                                        Authentication authentication, Model model){
        extractUserFromProvider(model, authentication);
        Playlist playlist = playlistService.getPlaylistById(id);
        if (photoUrl != null && !photoUrl.isEmpty()) {
            // Update the photo URL in the UserDto object
            playlist.setPhotoUrl(photoUrl);
        }
        playlistService.updatePlaylist(playlist);
        return "redirect:/playlists/"+id;
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
