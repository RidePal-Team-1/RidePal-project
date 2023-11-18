package com.example.ridepal.deezer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeezerTrack {
    private long id;
    private boolean readable;
    private String title;
    private String title_short;
    private String title_version;
    private String isrc;
    private String link;
    private int duration;
    private int rank;
    private boolean explicit_lyrics;
    private int explicit_content_lyrics;
    private int explicit_content_cover;
    private String preview;
    private String md5_image;
    private long time_add;
    private DeezerArtist artist;
    private DeezerAlbum album;
    private String type;
}