package com.example.drift.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    
    private String secret = "mySecretKey123456789012345678901234567890123456789012345678901234567890";
    private long expiration = 86400000; // 24 часа в миллисекундах
    private long refreshExpiration = 604800000; // 7 дней в миллисекундах
    private String headerName = "Authorization";
    private String tokenPrefix = "Bearer ";
}
