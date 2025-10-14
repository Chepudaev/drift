package com.example.drift.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(Map<String, String> errors) {
        super("Ошибка валидации данных");
        this.errors = errors;
    }
}

