package com.example.drift.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.drift.dto.ImageUploadResponse;
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
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:63342"},
        allowCredentials = "true")
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

    @Operation(
            summary = "Загрузить изображение",
            description = "Загружает изображение на сервер и возвращает URL для использования. Поддерживаемые форматы: JPG, PNG, GIF, WEBP. Максимальный размер: 10MB."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", 
                    description = "Изображение успешно загружено",
                    content = @Content(schema = @Schema(implementation = ImageUploadResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Неверный файл или пустой файл", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> uploadImage(
            @Parameter(description = "Файл изображения для загрузки", required = true)
            @RequestParam("file") MultipartFile file
    ) {
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

            ImageUploadResponse response = new ImageUploadResponse(fileUrl, filename);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Удалить изображение",
            description = "Удаляет изображение с сервера по имени файла"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Файл не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @DeleteMapping("/files/{filename:.+}")
    public ResponseEntity<Void> deleteFile(
            @Parameter(description = "Имя файла изображения для удаления", required = true, example = "abc123.jpg")
            @PathVariable String filename
    ) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Обрезать и обработать изображение",
            description = "Обрезает изображение и при необходимости делает его круглым (как аватар). " +
                         "Можно использовать два режима:\n" +
                         "1. Круглый аватар (circular=true) - автоматически создает круглое изображение\n" +
                         "2. Прямоугольная обрезка - указать координаты x, y и размеры width, height"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение успешно обработано",
                    content = @Content(schema = @Schema(implementation = ImageUploadResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Неверные параметры или формат файла", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping(value = "/crop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> cropImage(
            @Parameter(description = "Файл изображения для обработки", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Координата X левого верхнего угла области обрезки", example = "0")
            @RequestParam(value = "x", defaultValue = "0") int x,
            @Parameter(description = "Координата Y левого верхнего угла области обрезки", example = "0")
            @RequestParam(value = "y", defaultValue = "0") int y,
            @Parameter(description = "Ширина области обрезки (требуется для прямоугольной обрезки)", example = "400")
            @RequestParam(value = "width", required = false) Integer width,
            @Parameter(description = "Высота области обрезки (требуется для прямоугольной обрезки)", example = "400")
            @RequestParam(value = "height", required = false) Integer height,
            @Parameter(description = "Создать круглое изображение (аватар). Если true, параметры x, y, width, height игнорируются", example = "false")
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

            ImageUploadResponse response = new ImageUploadResponse(fileUrl, filename);

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

