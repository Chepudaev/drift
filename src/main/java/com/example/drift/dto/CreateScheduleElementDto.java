package com.example.drift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleElementDto {
    
    @NotBlank(message = "Время начала обязательно для заполнения")
    @Pattern(regexp = "^(1[0-2]|[1-9]):[0-5][0-9](AM|PM)$", 
             message = "Время должно быть в формате HH:MMAM или HH:MMPM (например: 10:40AM или 10:50PM)")
    private String startTime;
    
    @NotBlank(message = "Время окончания обязательно для заполнения")
    @Pattern(regexp = "^(1[0-2]|[1-9]):[0-5][0-9](AM|PM)$", 
             message = "Время должно быть в формате HH:MMAM или HH:MMPM (например: 10:40AM или 10:50PM)")
    private String endTime;
    
    @NotBlank(message = "Описание обязательно для заполнения")
    private String description;
    
    private Long scheduleId;
}
