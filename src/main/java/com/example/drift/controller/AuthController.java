package com.example.drift.controller;

import com.example.drift.dto.LoginRequest;
import com.example.drift.dto.LoginResponse;
import com.example.drift.entity.Role;
import com.example.drift.entity.UserEntity;
import com.example.drift.repository.UserRepository;
import com.example.drift.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Проверяем пользователя
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Неверные учетные данные"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверные учетные данные");
        }

        // Создаем аутентификацию
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), 
                user.getPassword(),
                userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities()
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                "Успешная аутентификация"
        ));
    }

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

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(Authentication authentication) {
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
