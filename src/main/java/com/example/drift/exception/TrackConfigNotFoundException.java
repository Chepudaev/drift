package com.example.drift.exception;

public class TrackConfigNotFoundException extends RuntimeException {
    public TrackConfigNotFoundException(String message) {
        super(message);
    }
}

