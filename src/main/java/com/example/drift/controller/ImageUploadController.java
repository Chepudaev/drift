package com.example.drift.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.drift.service.ImageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@Tag(name = "Загрузка изображений", description = "API для загрузки и обработки изображений")
@SecurityRequirement(name = "Bearer Authentication")
public class ImageUploadController {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    private final ImageProcessingService imageProcessingService;

    @Operation(summary = "Загрузить изображение", description = "Загружает изображение на сервер и возвращает URL для использования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно загружено"),
            @ApiResponse(responseCode = "400", description = "Неверный файл", content = @Content)
    })
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Валидация файла
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Проверка типа файла
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().build();
            }

            // Генерация уникального имени файла
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // Сохранение файла
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Формирование URL
            String fileUrl = baseUrl + "/api/files/" + filename;

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("filename", filename);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Получить изображение", description = "Возвращает изображение по имени файла")
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
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

    @Operation(summary = "Удалить изображение", description = "Удаляет изображение с сервера")
    @DeleteMapping("/files/{filename:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Обрезать и обработать изображение", 
               description = "Обрезает изображение и при необходимости делает его круглым (как аватар)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно обработано"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры", content = @Content)
    })
    @PostMapping(value = "/crop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> cropImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "x", defaultValue = "0") int x,
            @RequestParam(value = "y", defaultValue = "0") int y,
            @RequestParam(value = "width", required = false) Integer width,
            @RequestParam(value = "height", required = false) Integer height,
            @RequestParam(value = "circular", defaultValue = "false") boolean circular
    ) {
        try {
            byte[] imageBytes = imageProcessingService.multipartFileToBytes(file);
            byte[] processedBytes;

            if (circular) {
                // Создаем круглое изображение
                processedBytes = imageProcessingService.createCircularImage(imageBytes);
            } else if (width != null && height != null) {
                // Обрезаем изображение
                processedBytes = imageProcessingService.cropImage(imageBytes, x, y, width, height);
            } else {
                return ResponseEntity.badRequest().build();
            }

            // Генерируем новое имя файла
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + "_processed" + extension;

            // Сохраняем обработанное изображение
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filepath = uploadPath.resolve(filename).toString();
            imageProcessingService.saveBytesToFile(processedBytes, filepath);

            // Формируем URL
            String fileUrl = baseUrl + "/api/files/" + filename;

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("filename", filename);

            return ResponseEntity.ok(response);

        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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

