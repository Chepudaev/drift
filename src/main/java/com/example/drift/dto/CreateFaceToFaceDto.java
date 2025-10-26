package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO для создания Face-to-Face соревнования")
public class CreateFaceToFaceDto {

    @Schema(description = "Время начала соревнования", example = "10:30AM", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String startTime;

    @Schema(description = "URL фотографии первого пользователя", example = "https://example.com/user1_photo.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String userPhoto1;

    @Schema(description = "URL фотографии второго пользователя", example = "https://example.com/user2_photo.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String userPhoto2;

    @Schema(description = "ID первого пользователя", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long user1Id;

    @Schema(description = "ID второго пользователя", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long user2Id;

    @Schema(description = "URL фотографии первого автомобиля", example = "https://example.com/auto1_photo.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String autoPhoto1;

    @Schema(description = "URL фотографии второго автомобиля", example = "https://example.com/auto2_photo.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String autoPhoto2;

    @Schema(description = "ID первого автомобиля", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long autoUser1Id;

    @Schema(description = "ID второго автомобиля", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long autoUser2Id;

    @NotNull(message = "ID события обязательно для заполнения")
    @Schema(description = "ID события", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long eventId;
}
