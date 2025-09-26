package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    private Long id;

    @NotNull(message = "Дата события обязательна для заполнения")
    private LocalDate date;

    private TrackDto track;

    private List<FaceToFaceDto> faceToFaceEntities;

    private ScheduleDto schedule;

    @Min(value = 1, message = "Лимит водителей должен быть не менее 1")
    @NotNull(message = "Лимит водителей обязателен для заполнения")
    private Integer driverLimit;

    @Min(value = 0, message = "Лимит зрителей должен быть не менее 0")
    @NotNull(message = "Лимит зрителей обязателен для заполнения")
    private Integer spectatorLimit;

    @NotBlank(message = "Тип события обязателен для заполнения")
    private String eventType;
}
