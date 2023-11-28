package com.example.ridepal.controllers.mvc;

import com.example.ridepal.filters.enums.PlaylistSortField;
import com.example.ridepal.maps.BingResult;
import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.mappers.PlaylistMapper;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.models.dtos.PlaylistFiltersDto;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.PlaylistUpdateDto;
import com.example.ridepal.services.contracts.PlaylistService;
import com.example.ridepal.services.contracts.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/playlists")
@Slf4j
public class PlaylistMvcController {
    private final PlaylistService playlistService;

    private final UserService userService;

    private final PlaylistMapper playlistMapper;

    public PlaylistMvcController(PlaylistService playlistService, PlaylistMapper playlistMapper, UserService userService) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public String playlistView(@PathVariable int id, Model model, Authentication authentication){
//        try{
        extractUserFromProvider(model,authentication);
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

    @GetMapping
    public String showPlaylists(@ModelAttribute("filterOptions") PlaylistFiltersDto playlistFilterDto,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int sizePerPage,
                                @RequestParam(defaultValue = "RANK") PlaylistSortField sortField,
                                @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                Model model,
                                Authentication authentication) {

        extractUserFromProvider(model, authentication);
        Pageable pageable = PageRequest.of(page, sizePerPage, sortDirection,sortField.getDatabaseFieldName());
        Page<Playlist> playlists = playlistService.findAll(playlistFilterDto.getTitle(), playlistFilterDto.getGenre(),
                playlistFilterDto.getMinDuration(), playlistFilterDto.getMaxDuration(), pageable);

        int totalPages = playlists.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("playlists", playlists);
        model.addAttribute("filterOptions", playlistFilterDto);
        return "PlaylistsView";
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
