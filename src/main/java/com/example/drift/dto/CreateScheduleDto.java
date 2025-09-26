package com.example.drift.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleDto {
    
    @NotBlank(message = "Название расписания обязательно для заполнения")
    private String name;
    
    private String description;
    
    private List<CreateScheduleElementDto> scheduleElements;
}
