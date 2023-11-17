package com.example.ridepal.models.dtos;

public class TrackDto {
    private String artist;

    private String title;

    private String album;

    public TrackDto(String artist, String title, String album) {
        this.artist = artist;
        this.title = title;
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
