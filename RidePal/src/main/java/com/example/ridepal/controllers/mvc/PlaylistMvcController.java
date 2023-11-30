package com.example.ridepal.controllers.mvc;

import com.example.ridepal.exceptions.UnauthorizedOperationException;
import com.example.ridepal.filters.enums.PlaylistSortField;
import com.example.ridepal.helpers.AuthenticationHelper;
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
import org.springframework.http.HttpStatus;
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
public class PlaylistMvcController {
    private final PlaylistService playlistService;

    private final PlaylistMapper playlistMapper;

    public PlaylistMvcController(PlaylistService playlistService, PlaylistMapper playlistMapper) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
    }

    @GetMapping
    public String showPlaylists(@ModelAttribute("filterOptions") PlaylistFiltersDto playlistFilterDto,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int sizePerPage,
                                @RequestParam(defaultValue = "RANK") PlaylistSortField sortField,
                                @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                Model model, Authentication authentication) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
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
        model.addAttribute("user", user);
        model.addAttribute("playlists", playlists);
        model.addAttribute("filterOptions", playlistFilterDto);
        return "PlaylistsView";
    }

    @GetMapping("/{id}")
    public String playlistView(@PathVariable int id, Model model, Authentication authentication){
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            Playlist playlist = playlistService.getPlaylistById(id);
            model.addAttribute("playlist", playlist);
            model.addAttribute("user", user);
            return "SinglePlaylistView";

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "Error";
        }
    }

    @PutMapping("/{id}/update")
    public String updatePlaylist(@PathVariable int id,
                                 @Valid PlaylistUpdateDto playlistDto,
                                 BindingResult bindingResult,Model model, Authentication authentication){
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        if(bindingResult.hasErrors()){
            return "SinglePlaylistView";
        }
        try {
            model.addAttribute("id", id);
            model.addAttribute("user", user);
            Playlist playlist = playlistMapper.fromDto(playlistDto,id);
            playlistService.updatePlaylist(playlist, user);
            return "redirect:/playlists/{id}";
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "Error";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "Error";
        }
    }

    @PutMapping("/{id}/picture")
    public String updatePlaylistPicture(@PathVariable int id,
                                 @RequestParam(value = "photoUrl", required = false) String photoUrl,
                                        Authentication authentication, Model model){
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            Playlist playlist = playlistService.getPlaylistById(id);
            if (photoUrl != null && !photoUrl.isEmpty()) {
                // Update the photo URL in the UserDto object
                playlist.setPhotoUrl(photoUrl);
            }
            model.addAttribute("user", user);
            playlistService.updatePlaylist(playlist, user);
            return "redirect:/playlists/" + id;
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "Error";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "Error";
        }
    }

    @DeleteMapping("/{id}/delete")
    public String deletePlaylist(@PathVariable int id, Authentication authentication, Model model) {
        User user = AuthenticationHelper.extractUserFromProvider(authentication);
        try {
            playlistService.deletePlaylist(id, user);
            return "redirect:/playlists";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            return "Error";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "Error";
       }
    }
}
