package com.example.drift.service;

import com.example.drift.entity.Role;
import com.example.drift.entity.UserEntity;
import com.example.drift.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Создаем пользователя admin, если его еще нет
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));
            admin.setFirstName("Администратор");
            admin.setLastName("Системы");
            admin.setEmail("admin@drift.com");
            admin.setPhone("+79000000000");
            admin.setInstagram("@admin");
            admin.setMotto("Управляю системой дрифта");
            
            userRepository.save(admin);
            System.out.println("Создан пользователь admin с паролем admin");
        }
    }
}
