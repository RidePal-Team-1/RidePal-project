package com.example.ridepal.filters.enums;

public enum TrackSortField {
    ID("id"),
    RANK("rank");

    TrackSortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }

    private final String databaseFieldName;

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }
}
