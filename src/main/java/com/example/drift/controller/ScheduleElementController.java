package com.example.drift.controller;

import com.example.drift.dto.CreateScheduleElementDto;
import com.example.drift.dto.ScheduleElementDto;
import com.example.drift.dto.UpdateScheduleElementDto;
import com.example.drift.service.ScheduleElementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-elements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleElementController {
    
    private final ScheduleElementService scheduleElementService;
    
    @GetMapping
    public ResponseEntity<List<ScheduleElementDto>> getAllScheduleElements() {
        List<ScheduleElementDto> elements = scheduleElementService.getAllScheduleElements();
        return ResponseEntity.ok(elements);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> getScheduleElementById(@PathVariable Long id) {
        ScheduleElementDto element = scheduleElementService.getScheduleElementById(id);
        return ResponseEntity.ok(element);
    }
    
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<ScheduleElementDto>> getScheduleElementsByScheduleId(@PathVariable Long scheduleId) {
        List<ScheduleElementDto> elements = scheduleElementService.getScheduleElementsByScheduleId(scheduleId);
        return ResponseEntity.ok(elements);
    }
    
    @PostMapping
    public ResponseEntity<ScheduleElementDto> createScheduleElement(@Valid @RequestBody CreateScheduleElementDto createScheduleElementDto) {
        ScheduleElementDto createdElement = scheduleElementService.createScheduleElement(createScheduleElementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElement);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> updateScheduleElement(@PathVariable Long id, @Valid @RequestBody UpdateScheduleElementDto updateScheduleElementDto) {
        ScheduleElementDto updatedElement = scheduleElementService.updateScheduleElement(id, updateScheduleElementDto);
        return ResponseEntity.ok(updatedElement);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> patchScheduleElement(@PathVariable Long id, @Valid @RequestBody UpdateScheduleElementDto patchScheduleElementDto) {
        ScheduleElementDto updatedElement = scheduleElementService.updateScheduleElement(id, patchScheduleElementDto);
        return ResponseEntity.ok(updatedElement);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleElement(@PathVariable Long id) {
        scheduleElementService.deleteScheduleElement(id);
        return ResponseEntity.noContent().build();
    }
}
