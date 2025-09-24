package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoundDto {

    private Long id;

    private Integer roundNumber;

    private Long userId1;

    private Long userId2;

    private Long userWinnerId;

    private Long faceToFaceId;
}
