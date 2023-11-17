package com.example.ridepal.filters;

public enum PlaylistSortField {
    ID("id"),
    RANK("rank");

    PlaylistSortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }

    private final String databaseFieldName;

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }
}
