package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO для Face-to-Face соревнования (ответ)")
public class FaceToFaceDto {

    @Schema(description = "ID соревнования", example = "1")
    private Long id;

    @Schema(description = "Время начала соревнования", example = "10:30AM")
    private String startTime;

    @Schema(description = "URL фотографии первого пользователя", example = "https://example.com/user1_photo.jpg")
    private String userPhoto1;

    @Schema(description = "URL фотографии второго пользователя", example = "https://example.com/user2_photo.jpg")
    private String userPhoto2;

    @Schema(description = "ID первого пользователя", example = "1")
    private Long user1Id;

    @Schema(description = "ID второго пользователя", example = "2")
    private Long user2Id;

    @Schema(description = "URL фотографии первого автомобиля", example = "https://example.com/auto1_photo.jpg")
    private String autoPhoto1;

    @Schema(description = "URL фотографии второго автомобиля", example = "https://example.com/auto2_photo.jpg")
    private String autoPhoto2;

    @Schema(description = "ID первого автомобиля", example = "1")
    private Long autoUser1Id;

    @Schema(description = "ID второго автомобиля", example = "2")
    private Long autoUser2Id;

    @Schema(description = "ID события", example = "1")
    private Long eventId;

    @Schema(description = "Список раундов")
    private List<RoundDto> rounds;
}
