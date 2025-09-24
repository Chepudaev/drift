package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRoundDto {

    @NotNull(message = "Номер раунда обязателен для заполнения")
    private Integer roundNumber;

    @NotNull(message = "ID первого пользователя обязателен для заполнения")
    private Long userId1;

    @NotNull(message = "ID второго пользователя обязателен для заполнения")
    private Long userId2;

    private Long userWinnerId;

    @NotNull(message = "ID FaceToFace обязателен для заполнения")
    private Long faceToFaceId;
}
