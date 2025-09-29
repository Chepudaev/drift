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
public class FaceToFaceDto {

    private Long id;

    @NotBlank(message = "Время начала обязательно для заполнения")
    private String startTime;

    private String userPhoto1;
    private String userPhoto2;
    private Long user1Id;
    private Long user2Id;
    private String autoPhoto1;
    private String autoPhoto2;
    private Long autoUser1Id;
    private Long autoUser2Id;
    private Long eventId;

    private List<RoundDto> rounds;
}
