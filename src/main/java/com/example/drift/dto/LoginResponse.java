package com.example.drift.dto;

import com.example.drift.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private String username;
    private Set<Role> roles;
    private String message;
    private String accessToken;
    private String refreshToken;
    
    // Конструктор для обратной совместимости (без токенов)
    public LoginResponse(Long userId, String username, Set<Role> roles, String message) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.message = message;
    }
}
