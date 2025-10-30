package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackConfigDto {

    private Long id;

    @NotBlank(message = "Конфигурация трассы обязательна для заполнения")
    private String config;

    private Long trackId;

    private String mainMapConfigMobileUrl;

    private String mainMapConfigWebUrl;
}







