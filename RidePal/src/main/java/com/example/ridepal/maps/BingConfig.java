package com.example.ridepal.maps;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class BingConfig {
    @Value("${bing.api.base-url}")
    private String baseUrl;
    @Value("${bingmaps.api.key}")
    private String apiKey;
}
