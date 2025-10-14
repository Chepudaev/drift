package com.example.drift.dto;

import com.example.drift.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для отображения информации о пользователе")
public class UserDto {
    
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;
    
    @Schema(description = "Полное имя пользователя", example = "Иван Иванов")
    @NotBlank(message = "Полное имя обязательно для заполнения")
    private String name;
    
    @Schema(description = "Имя пользователя для входа в систему", example = "ivan_ivanov")
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String username;
    
    @Schema(description = "Имя", example = "Иван")
    @NotBlank(message = "Имя обязательно для заполнения")
    private String firstName;
    
    @Schema(description = "Фамилия", example = "Иванов")
    @NotBlank(message = "Фамилия обязательна для заполнения")
    private String lastName;
    
    @Schema(description = "Email адрес", example = "ivan.ivanov@example.com")
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;
    
    @Schema(description = "Номер телефона", example = "+7-911-123-4567")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некорректный формат телефона")
    @NotBlank(message = "Телефон обязателен для заполнения")
    private String phone;
    
    @Schema(description = "Instagram аккаунт", example = "@ivan_drift")
    private String instagram;
    
    @Schema(description = "URL фото профиля", example = "https://example.com/photos/profile.jpg")
    private String profilePhotoUrl;
    
    @Schema(description = "Девиз пользователя", example = "Дрифт - это искусство")
    private String motto;
    
    @Schema(description = "URL официального фото", example = "https://example.com/photos/official.jpg")
    private String officialPhotoUrl;
    
    @Schema(description = "Список спонсоров", example = "Red Bull, Toyota Racing")
    private String sponsors;
    
    @Schema(description = "Роли пользователя", example = "[\"ROLE_USER\"]")
    private Set<Role> roles;
    
    // Note: cars field removed to avoid circular dependency
    // Use separate endpoint to get user's cars
    // Note: password field excluded for security reasons
}