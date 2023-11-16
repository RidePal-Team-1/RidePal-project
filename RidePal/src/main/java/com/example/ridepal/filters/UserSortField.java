package com.example.ridepal.filters;

public enum UserSortField {

    ID("id"),
    USERNAME("username"),
    FIRSTNAME("first_name"),
    LASTNAME("last_name"),
    EMAIL("email");


    UserSortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }

    private final String databaseFieldName;

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }

}
