package com.example.drift.controller;

import com.example.drift.dto.LoginRequest;
import com.example.drift.dto.LoginResponse;
import com.example.drift.dto.RefreshTokenRequest;
import com.example.drift.entity.Role;
import com.example.drift.entity.UserEntity;
import com.example.drift.repository.UserRepository;
import com.example.drift.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "API для аутентификации и регистрации пользователей")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Operation(summary = "Вход в систему", description = "Аутентификация пользователя и получение JWT токенов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверный формат запроса", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = jwtService.authenticateAndGenerateTokens(
                request.getUsername(), 
                request.getPassword()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Создание нового пользователя в системе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Пользователь уже существует или неверный формат данных", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        user.setFirstName("Пользователь");
        user.setLastName("Новый");
        user.setEmail(request.getUsername() + "@example.com");
        user.setPhone("+79000000000");

        UserEntity savedUser = userRepository.save(user);

        return ResponseEntity.ok(new LoginResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRoles(),
                "Пользователь успешно зарегистрирован"
        ));
    }

    @Operation(summary = "Обновление токенов", description = "Обновление access и refresh токенов с помощью refresh токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токены успешно обновлены",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Невалидный refresh токен", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверный формат запроса", content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = jwtService.refreshTokens(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить текущего пользователя", description = "Получение информации о текущем аутентифицированном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Пользователь не аутентифицирован. Необходимо передать валидный JWT токен в заголовке Authorization: Bearer <token>");
        }

        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return ResponseEntity.ok(new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                "Текущий пользователь"
        ));
    }
}
