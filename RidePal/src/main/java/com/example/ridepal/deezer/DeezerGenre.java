package com.example.ridepal.deezer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerGenre {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
}
//    private String picture;
//    private String pictureSmall;
//    private String pictureMedium;
//    private String pictureBig;
//    private String pictureXl;
    
    
