package com.example.drift.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ после загрузки изображения")
public class ImageUploadResponse {

    @Schema(description = "URL загруженного изображения", example = "http://localhost:8080/api/files/abc123.jpg")
    private String url;

    @Schema(description = "Имя файла", example = "abc123.jpg")
    private String filename;
}

