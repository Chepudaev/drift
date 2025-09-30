package com.example.drift.service;

import com.example.drift.dto.LoginResponse;
import com.example.drift.entity.UserEntity;
import com.example.drift.repository.UserRepository;
import com.example.drift.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse authenticateAndGenerateTokens(String username, String password) {
        // Аутентификация пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерация токенов
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(username);

        // Получение информации о пользователе
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                "Успешная аутентификация",
                jwtToken,
                refreshToken
        );
    }

    public LoginResponse refreshTokens(String refreshToken) {
        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new RuntimeException("Недействительный refresh token");
        }

        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Генерация новых токенов
        String newJwtToken = jwtUtils.generateJwtTokenFromUsername(username);
        String newRefreshToken = jwtUtils.generateRefreshToken(username);

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                "Токены обновлены",
                newJwtToken,
                newRefreshToken
        );
    }
}
