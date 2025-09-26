package com.example.drift.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleDto {
    
    private String name;
    
    private String description;
    
    private List<UpdateScheduleElementDto> scheduleElements;
}
