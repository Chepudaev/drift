package com.example.drift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "Полное имя обязательно для заполнения")
    private String name;
    
    @NotBlank(message = "Имя обязательно для заполнения")
    private String firstName;
    
    @NotBlank(message = "Фамилия обязательна для заполнения")
    private String lastName;
    
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некорректный формат телефона")
    @NotBlank(message = "Телефон обязателен для заполнения")
    private String phone;
    
    private String instagram;
    
    private String profilePhotoUrl;
    
    private String motto;
    
    private String officialPhotoUrl;
    
    private String sponsors;
    
    // Note: cars field removed to avoid circular dependency
    // Use separate endpoint to get user's cars
}