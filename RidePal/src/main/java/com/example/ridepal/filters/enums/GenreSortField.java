package com.example.ridepal.filters.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenreSortField {

    ID("id");

    private final String databaseFieldName;
}
