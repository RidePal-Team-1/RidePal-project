package com.example.ridepal.filters;

public enum PlaylistSortField {
    ID("id"),
    TITLE("title"),
    MIN_RANK("rank"),
    MAX_RANK("rank"),
    GENRE("genres");


    PlaylistSortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }

    private final String databaseFieldName;

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }
}
