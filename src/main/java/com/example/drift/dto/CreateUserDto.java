package com.example.drift.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания нового пользователя")
public class CreateUserDto {
    
    @Schema(description = "Полное имя пользователя", example = "Иван Иванов")
    @NotBlank(message = "Полное имя обязательно для заполнения")
    private String name;
    
    @Schema(description = "Имя пользователя для входа в систему", example = "ivan_ivanov")
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    private String username;
    
    @Schema(description = "Пароль пользователя", example = "securePassword123")
    @NotBlank(message = "Пароль обязателен для заполнения")
    @Size(min = 6, max = 100, message = "Пароль должен содержать от 6 до 100 символов")
    private String password;
    
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
}
