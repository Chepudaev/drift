package com.example.drift.controller;

import com.example.drift.dto.CreateScheduleDto;
import com.example.drift.dto.ScheduleDto;
import com.example.drift.dto.UpdateScheduleDto;
import com.example.drift.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Tag(name = "Расписания", description = "API для управления расписаниями событий")
@SecurityRequirement(name = "Bearer Authentication")
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @Operation(summary = "Получить все расписания", description = "Возвращает список всех расписаний")
    @ApiResponse(responseCode = "200", description = "Список расписаний")
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @Operation(summary = "Получить расписание по ID", description = "Возвращает расписание по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание найдено",
                    content = @Content(schema = @Schema(implementation = ScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        ScheduleDto schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }
    
    @Operation(summary = "Создать расписание", description = "Создает новое расписание")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Расписание создано",
                    content = @Content(schema = @Schema(implementation = ScheduleDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@Valid @RequestBody CreateScheduleDto createScheduleDto) {
        ScheduleDto createdSchedule = scheduleService.createSchedule(createScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }
    
    @Operation(summary = "Полностью обновить расписание", description = "Полностью обновляет существующее расписание")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание обновлено",
                    content = @Content(schema = @Schema(implementation = ScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @Valid @RequestBody UpdateScheduleDto updateScheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, updateScheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    @Operation(summary = "Частично обновить расписание", description = "Частично обновляет существующее расписание")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расписание обновлено",
                    content = @Content(schema = @Schema(implementation = ScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto> patchSchedule(@PathVariable Long id, @Valid @RequestBody UpdateScheduleDto patchScheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, patchScheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }
    
    @Operation(summary = "Удалить расписание", description = "Удаляет расписание по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Расписание удалено"),
            @ApiResponse(responseCode = "404", description = "Расписание не найдено", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Скопировать расписание", description = "Создает копию существующего расписания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Расписание скопировано",
                    content = @Content(schema = @Schema(implementation = ScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Исходное расписание не найдено", content = @Content)
    })
    @PostMapping("/{id}/copy")
    public ResponseEntity<ScheduleDto> copySchedule(@PathVariable Long id) {
        ScheduleDto copiedSchedule = scheduleService.copySchedule(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(copiedSchedule);
    }
}
