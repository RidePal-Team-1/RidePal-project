package com.example.ridepal.filters.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtistSortField {

    ID("id");

    private final String databaseFieldName;
}
