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
public class EventTrackConfigDto {

    private Long id;

    @NotNull(message = "Событие обязательно для заполнения")
    private EventDto event;

    private TrackDto track;

    private TrackConfigDto trackConfig;
}




