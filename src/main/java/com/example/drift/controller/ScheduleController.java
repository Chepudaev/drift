package com.example.drift.controller;

import com.example.drift.dto.CreateScheduleDto;
import com.example.drift.dto.ScheduleDto;
import com.example.drift.dto.UpdateScheduleDto;
import com.example.drift.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        ScheduleDto schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }
    
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@Valid @RequestBody CreateScheduleDto createScheduleDto) {
        ScheduleDto createdSchedule = scheduleService.createSchedule(createScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @Valid @RequestBody UpdateScheduleDto updateScheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, updateScheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto> patchSchedule(@PathVariable Long id, @Valid @RequestBody UpdateScheduleDto patchScheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, patchScheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/copy")
    public ResponseEntity<ScheduleDto> copySchedule(@PathVariable Long id) {
        ScheduleDto copiedSchedule = scheduleService.copySchedule(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(copiedSchedule);
    }
}
