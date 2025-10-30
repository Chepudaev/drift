package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTrackConfigDto {

    private String config;

    private Long trackId;

    private String mainMapConfigMobileUrl;

    private String mainMapConfigWebUrl;
}



