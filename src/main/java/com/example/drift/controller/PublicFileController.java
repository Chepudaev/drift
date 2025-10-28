package com.example.drift.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Публичный контроллер для доступа к файлам без аутентификации
 */
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:63342"})
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Публичный доступ к файлам", description = "API для публичного доступа к загруженным файлам без аутентификации")
public class PublicFileController {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Operation(
            summary = "Получить изображение",
            description = "Возвращает файл по имени без необходимости аутентификации. Используется для отображения изображений в браузере."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл найден"),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content)
    })
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(
            @Parameter(description = "Имя файла", required = true, example = "abc123.jpg")
            @PathVariable String filename
    ) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}

