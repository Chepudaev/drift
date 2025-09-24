package com.example.drift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Пользователь не найден");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCarNotFoundException(CarNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Машина не найдена");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Пользователь уже существует");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTrackNotFoundException(TrackNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Трасса не найдена");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(TrackConfigNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTrackConfigNotFoundException(TrackConfigNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Конфигурация трассы не найдена");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(FaceToFaceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFaceToFaceNotFoundException(FaceToFaceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "FaceToFace не найдена");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(RoundNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoundNotFoundException(RoundNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Раунд не найден");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("error", "Ошибка валидации");
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Внутренняя ошибка сервера");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}