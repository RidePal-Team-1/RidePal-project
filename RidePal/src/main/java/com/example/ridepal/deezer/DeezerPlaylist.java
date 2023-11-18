package com.example.ridepal.deezer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeezerPlaylist {
    private long id;
    private String title;
    private boolean isPublic;
    private int nb_tracks;
    private String link;
    private String picture;
    private String picture_medium;
    private String picture_big;
    private String picture_xl;
    private String checksum;
    private String tracklist;
    private String creation_date;
    private String md5_image;
    private String picture_type;
    private DeezerCreator user;
    private String type;
}

