package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackDto {

    private Long id;

    @NotBlank(message = "Штат обязателен для заполнения")
    private String state;

    @NotBlank(message = "Адрес трассы обязателен для заполнения")
    private String address;

    private String thumbnailUrl;

    private List<TrackConfigDto> trackConfigs;

    private String instructionUrl;

    private String notes;

    private String mainMapMobileUrl;

    private String mainMapWebUrl;
}





