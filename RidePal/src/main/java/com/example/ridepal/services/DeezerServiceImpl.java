package com.example.ridepal.services;

import com.example.ridepal.deezer.*;
import com.example.ridepal.models.*;
import com.example.ridepal.repositories.AlbumRepository;
import com.example.ridepal.repositories.ArtistRepository;
import com.example.ridepal.repositories.GenreRepository;
import com.example.ridepal.repositories.TrackRepository;
import com.example.ridepal.services.contracts.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeezerServiceImpl implements DeezerService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private final GenreRepository genreRepository;

    private final TrackRepository trackRepository;

    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    @Autowired
    public DeezerServiceImpl(RestTemplate restTemplate, @Value("${deezer.api.base-url}") String baseUrl,
                             GenreRepository genreRepository, TrackRepository trackRepository,
                             ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.genreRepository = genreRepository;
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void getGenres() {
        String genresEndpoint = baseUrl + "/genre";
        DeezerGenresResponse response = restTemplate.getForObject(genresEndpoint, DeezerGenresResponse.class);
        if (response != null && response.getData() != null) {
            List<DeezerGenre> genres = response.getData();
            saveGenresToDatabase(genres);
        }
    }

    @Override
    public void getPlaylists(String genre) {
        String playlistsEndpoint = baseUrl+"/search/playlist?q="+genre+"&limit=10";
        DeezerPlaylistResponse response = restTemplate.getForObject(playlistsEndpoint, DeezerPlaylistResponse.class);
        if (response != null && response.getData() != null) {
            List<DeezerPlaylist> playlists = response.getData();
            savePlaylistsToDataBase(playlists, genre);
        }
    }

    @Override
    public void getTracks() {

    }

    private void saveGenresToDatabase(List<DeezerGenre> genres) {
        for (DeezerGenre deezerGenre : genres) {
            Genre genre = new Genre();
            genre.setId(deezerGenre.getId());
            genre.setName(deezerGenre.getName());
            genreRepository.save(genre);
        }
    }

    private void savePlaylistsToDataBase(List<DeezerPlaylist> playlists, String genre) {
         for(DeezerPlaylist deezerPlaylist : playlists){
             String trackList = deezerPlaylist.getTrackList();
             DeezerTrackResponse response = restTemplate.getForObject(trackList, DeezerTrackResponse.class);
             if (response != null && response.getData() != null) {
                 List<DeezerTrack> tracks = response.getData();
                 saveTracksToDataBase(tracks, genre);
             }
        }
    }

    private void saveTracksToDataBase(List<DeezerTrack> tracks, String genre) {
        for (DeezerTrack deezerTrack : tracks) {

            DeezerArtist deezerArtist = deezerTrack.getArtist();
            Artist artist = new Artist();
            artist.setId(deezerArtist.getId());
            artist.setName(deezerArtist.getName());
            artist.setTrackListUrl(deezerArtist.getTrackList());
            artist.setPhotoUrl(deezerArtist.getPhotoUrl());
            artistRepository.save(artist);

            DeezerAlbum deezerAlbum = deezerTrack.getAlbum();
            Album album = new Album();
            album.setId(deezerAlbum.getId());
            album.setName(deezerAlbum.getTitle());
            album.setUrl(deezerAlbum.getTrackList());
            album.setPhotoUrl(deezerAlbum.getPhotoUrl());
            albumRepository.save(album);

            Track track = new Track();
            track.setId(deezerTrack.getId());
            track.setTitle(deezerTrack.getTitle());
            track.setRank(deezerTrack.getRank());
            track.setPlaytime(deezerTrack.getDuration());
            track.setUrl(deezerTrack.getPreview());
            track.setArtist(artist);
            track.setAlbum(album);
            track.setGenre(genreRepository.findByName(genre));
            trackRepository.save(track);
        }
    }
}
