package com.example.drift.exception;

public class ScheduleElementNotFoundException extends RuntimeException {
    public ScheduleElementNotFoundException(String message) {
        super(message);
    }
}
