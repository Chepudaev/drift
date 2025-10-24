package com.example.drift.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI driftOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.1")
                .info(new Info()
                        .title("Drift Event Management API")
                        .version("2.0.0")
                        .description("""
                                ## REST API для управления дрифт-мероприятиями
                                
                                Этот API предоставляет полный функционал для управления дрифт-событиями, включая:
                                
                                ### Основные возможности:
                                - **Аутентификация и авторизация** - JWT токены с ролевой моделью доступа
                                - **Управление событиями** - создание, редактирование и удаление дрифт-событий
                                - **Управление треками** - настройка треков и их конфигураций
                                - **Управление пользователями** - регистрация, профили, роли
                                - **Управление автомобилями** - каталог автомобилей участников
                                - **Расписания** - планирование и управление расписаниями событий
                                - **Соревнования** - раунды и face-to-face соревнования
                                
                                ### Роли пользователей:
                                - **ROLE_USER** - базовые права доступа
                                - **ROLE_JUDGE** - права судьи для оценки соревнований
                                - **ROLE_MANAGER** - полные права управления
                                - **ROLE_ADMIN** - административные права
                                
                                ### Аутентификация:
                                API использует JWT токены. Для получения токена используйте эндпоинт `/api/auth/login` или `/api/auth/register`.
                                """)
                        .contact(new Contact()
                                .name("Drift Development Team")
                                .email("dev@drift.example.com")
                                .url("https://drift.example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Локальная среда разработки"),
                        new Server()
                                .url("https://api.drift.example.com")
                                .description("Продуктивная среда"),
                        new Server()
                                .url("https://staging-api.drift.example.com")
                                .description("Тестовая среда")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name("JWT Authentication")
                                        .description("""
                                                Введите JWT токен для аутентификации.
                                                
                                                Формат: `Bearer <your-jwt-token>`
                                                
                                                Для получения токена:
                                                1. Используйте `/api/auth/login` или `/api/auth/register`
                                                2. Скопируйте `accessToken` из ответа
                                                3. Вставьте в формате: `Bearer <accessToken>`
                                                """))
                        .addResponses("BadRequest", new ApiResponse()
                                .description("Неверный запрос")
                                .content(new io.swagger.v3.oas.models.media.Content()
                                        .addMediaType("application/json", 
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .schema(new io.swagger.v3.oas.models.media.Schema()
                                                                .type("object")
                                                                .addProperty("error", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("message", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema().type("string"))))))
                        .addResponses("Unauthorized", new ApiResponse()
                                .description("Неавторизованный доступ")
                                .content(new io.swagger.v3.oas.models.media.Content()
                                        .addMediaType("application/json", 
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .schema(new io.swagger.v3.oas.models.media.Schema()
                                                                .type("object")
                                                                .addProperty("error", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("message", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema().type("string"))))))
                        .addResponses("Forbidden", new ApiResponse()
                                .description("Доступ запрещен")
                                .content(new io.swagger.v3.oas.models.media.Content()
                                        .addMediaType("application/json", 
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .schema(new io.swagger.v3.oas.models.media.Schema()
                                                                .type("object")
                                                                .addProperty("error", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("message", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema().type("string"))))))
                        .addResponses("NotFound", new ApiResponse()
                                .description("Ресурс не найден")
                                .content(new io.swagger.v3.oas.models.media.Content()
                                        .addMediaType("application/json", 
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .schema(new io.swagger.v3.oas.models.media.Schema()
                                                                .type("object")
                                                                .addProperty("error", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("message", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema().type("string"))))))
                        .addResponses("InternalServerError", new ApiResponse()
                                .description("Внутренняя ошибка сервера")
                                .content(new io.swagger.v3.oas.models.media.Content()
                                        .addMediaType("application/json", 
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .schema(new io.swagger.v3.oas.models.media.Schema()
                                                                .type("object")
                                                                .addProperty("error", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("message", new io.swagger.v3.oas.models.media.Schema().type("string"))
                                                                .addProperty("timestamp", new io.swagger.v3.oas.models.media.Schema().type("string")))))));
    }
}

