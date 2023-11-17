package com.example.ridepal.services;

import com.example.ridepal.deezer.DeezerGenre;
import com.example.ridepal.deezer.DeezerGenresResponse;
import com.example.ridepal.models.Genre;
import com.example.ridepal.repositories.GenreRepository;
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
    private final String apiKey;

    private final GenreRepository genreRepository;

    @Autowired
    public DeezerServiceImpl(RestTemplate restTemplate, @Value("${deezer.api.base-url}") String baseUrl,
                             @Value("${deezer.api.key}") String apiKey, GenreRepository genreRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.genreRepository = genreRepository;
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

    private void saveGenresToDatabase(List<DeezerGenre> genres) {
        for (DeezerGenre deezerGenre : genres) {
            Genre genre = new Genre();
            genre.setName(deezerGenre.getName());
            genreRepository.save(genre);
        }
    }
}
