package com.example.ridepal.services;

import com.example.ridepal.external.services.PixabayServiceImpl;
import com.example.ridepal.pixabay.PixabayConfig;
import com.example.ridepal.pixabay.PixabayHits;
import com.example.ridepal.pixabay.PixabayResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PixabayServiceTests {

    @Mock
    RestTemplate restTemplate;

    @Mock
    PixabayConfig pixabayConfig;

    @InjectMocks
    PixabayServiceImpl pixabayService;

@Test
public void getPlaylistCoverUrl_Should_returnUrl() {
    String key = "0989901-e83737cc6c3f2358d09edb400";
    String baseUrl = "https://pixabay.com/api/" ;

    PixabayHits pixabayHits = new PixabayHits();
    pixabayHits.setUrl("url");

    PixabayResponse response = new PixabayResponse();
    List<PixabayHits> hits = new ArrayList<>();
    for(int i = 1; i<=20; i++){
        PixabayHits pixabayHits1 = new PixabayHits();
        pixabayHits1.setUrl("url");
        hits.add(pixabayHits1);
    }
    response.setData(hits);

    Mockito.when(pixabayConfig.getApiKey()).thenReturn(key);
    Mockito.when(pixabayConfig.getBaseUrl()).thenReturn(baseUrl);
    String url = String.format("%s?key=%s&q=music",pixabayConfig.getBaseUrl(),pixabayConfig.getApiKey());

    Mockito.when(restTemplate.getForObject(url, PixabayResponse.class)).thenReturn(response);

    String result = pixabayService.getPlaylistCoverUrl();

    Assertions.assertEquals("url",result);

}
}