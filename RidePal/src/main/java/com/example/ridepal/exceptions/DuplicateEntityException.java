package com.example.ridepal.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public DuplicateEntityException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
