package com.example.ridepal.controllers.mvc;

import com.example.ridepal.maps.BingResult;
import com.example.ridepal.models.Playlist;
import com.example.ridepal.models.dtos.PlaylistDto;
import com.example.ridepal.services.contracts.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlists")
public class PlaylistMvcController {
    private final PlaylistService playlistService;

    public PlaylistMvcController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping("/{id}")
    public String playlistView(@PathVariable int id, Model model){
//        try{
        model.addAttribute("playlist", playlistService.getPlaylistById(id));
//        }catch (EntityNotFoundException){
//            return "redirect:"
//        }
        return "SinglePlaylistView";
    }

//    @PostMapping("/{id}")
//    public String updatePlaylist(@PathVariable int id,
//                                 @Valid @ModelAttribute PlaylistDto playlistDto,
//                                 BindingResult bindingResult,
//                                 Model model){
//        if(bindingResult.hasErrors()){
//            return "SinglePlaylistView";
//        }
//        Playlist playlist = playlistService.getPlaylistById(id);
//        playlist = playlist
//    }

    @PostMapping("/{id}/picture")
    public String updatePlaylistPicture(@PathVariable int id,
                                 @RequestParam(value = "photoUrl", required = false) String photoUrl){
        Playlist playlist = playlistService.getPlaylistById(id);
        if (photoUrl != null && !photoUrl.isEmpty()) {
            // Update the photo URL in the UserDto object
            playlist.setPhotoUrl(photoUrl);
        }
        playlistService.updatePlaylist(playlist);
        return "redirect:/playlists/"+id;
    }
}
