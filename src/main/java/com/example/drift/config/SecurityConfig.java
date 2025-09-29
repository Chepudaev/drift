package com.example.drift.config;

import com.example.drift.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Публичные endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/greeting").permitAll()
                
                // GET запросы доступны всем аутентифицированным пользователям
                .requestMatchers("GET", "/api/**").authenticated()
                
                // POST /api/rounds доступен только JUDGE
                .requestMatchers("POST", "/api/rounds").hasRole("JUDGE")
                
                // PATCH /api/face-to-face/{id} доступен только JUDGE
                .requestMatchers("PATCH", "/api/face-to-face/*").hasRole("JUDGE")
                
                // Все остальные запросы требуют роли ADMIN или MANAGER
                .requestMatchers("/api/**").hasAnyRole("ADMIN", "MANAGER")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/api/greeting", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
