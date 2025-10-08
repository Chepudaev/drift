package com.example.drift.config;

import com.example.drift.entity.Role;
import com.example.drift.filter.JwtAuthenticationFilter;
import com.example.drift.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("=== SECURITY CONFIG LOADING ===");
        System.out.println("POST /api/face-to-face should be blocked for ROLE_USER");
        System.out.println("JWT Filter: " + jwtAuthenticationFilter.getClass().getName());
        log.info("=== Configuring Security Filter Chain ===");
        log.info("Security rules:");
        log.info("1. /api/auth/** - permitAll");
        log.info("2. /api/greeting - permitAll");
        log.info("3. /api/test/public - permitAll");
        log.info("4. GET /api/** - authenticated");
        log.info("5. POST /api/rounds - hasRole(JUDGE)");
        log.info("6. PATCH /api/face-to-face/* - hasRole(JUDGE)");
        log.info("7. /api/** - hasAnyRole(ADMIN, MANAGER)");
        log.info("8. anyRequest - authenticated");
        
        http
            .cors(cors -> cors.and())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Публичные endpoints
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/greeting").permitAll()
                .requestMatchers("/api/test/public").permitAll()
                
                // OpenAPI и Swagger UI endpoints
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                
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
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("=== Security Filter Chain configured successfully ===");
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
