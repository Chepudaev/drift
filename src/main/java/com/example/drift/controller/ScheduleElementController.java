package com.example.drift.controller;

import com.example.drift.dto.CreateScheduleElementDto;
import com.example.drift.dto.ScheduleElementDto;
import com.example.drift.dto.UpdateScheduleElementDto;
import com.example.drift.service.ScheduleElementService;
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
@RequestMapping("/api/schedule-elements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Элементы расписания", description = "API для управления элементами расписаний")
@SecurityRequirement(name = "Bearer Authentication")
public class ScheduleElementController {
    
    private final ScheduleElementService scheduleElementService;
    
    @Operation(summary = "Получить все элементы расписания", description = "Возвращает список всех элементов расписаний")
    @ApiResponse(responseCode = "200", description = "Список элементов расписания")
    @GetMapping
    public ResponseEntity<List<ScheduleElementDto>> getAllScheduleElements() {
        List<ScheduleElementDto> elements = scheduleElementService.getAllScheduleElements();
        return ResponseEntity.ok(elements);
    }
    
    @Operation(summary = "Получить элемент расписания по ID", description = "Возвращает элемент расписания по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Элемент найден",
                    content = @Content(schema = @Schema(implementation = ScheduleElementDto.class))),
            @ApiResponse(responseCode = "404", description = "Элемент не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> getScheduleElementById(@PathVariable Long id) {
        ScheduleElementDto element = scheduleElementService.getScheduleElementById(id);
        return ResponseEntity.ok(element);
    }
    
    @Operation(summary = "Получить элементы конкретного расписания", description = "Возвращает все элементы конкретного расписания")
    @ApiResponse(responseCode = "200", description = "Список элементов расписания")
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<ScheduleElementDto>> getScheduleElementsByScheduleId(@PathVariable Long scheduleId) {
        List<ScheduleElementDto> elements = scheduleElementService.getScheduleElementsByScheduleId(scheduleId);
        return ResponseEntity.ok(elements);
    }
    
    @Operation(summary = "Создать элемент расписания", description = "Создает новый элемент расписания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Элемент создан",
                    content = @Content(schema = @Schema(implementation = ScheduleElementDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ScheduleElementDto> createScheduleElement(@Valid @RequestBody CreateScheduleElementDto createScheduleElementDto) {
        ScheduleElementDto createdElement = scheduleElementService.createScheduleElement(createScheduleElementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElement);
    }
    
    @Operation(summary = "Полностью обновить элемент расписания", description = "Полностью обновляет существующий элемент расписания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Элемент обновлен",
                    content = @Content(schema = @Schema(implementation = ScheduleElementDto.class))),
            @ApiResponse(responseCode = "404", description = "Элемент не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> updateScheduleElement(@PathVariable Long id, @Valid @RequestBody UpdateScheduleElementDto updateScheduleElementDto) {
        ScheduleElementDto updatedElement = scheduleElementService.updateScheduleElement(id, updateScheduleElementDto);
        return ResponseEntity.ok(updatedElement);
    }
    
    @Operation(summary = "Частично обновить элемент расписания", description = "Частично обновляет существующий элемент расписания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Элемент обновлен",
                    content = @Content(schema = @Schema(implementation = ScheduleElementDto.class))),
            @ApiResponse(responseCode = "404", description = "Элемент не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleElementDto> patchScheduleElement(@PathVariable Long id, @Valid @RequestBody UpdateScheduleElementDto patchScheduleElementDto) {
        ScheduleElementDto updatedElement = scheduleElementService.updateScheduleElement(id, patchScheduleElementDto);
        return ResponseEntity.ok(updatedElement);
    }
    
    @Operation(summary = "Удалить элемент расписания", description = "Удаляет элемент расписания по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Элемент удален"),
            @ApiResponse(responseCode = "404", description = "Элемент не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleElement(@PathVariable Long id) {
        scheduleElementService.deleteScheduleElement(id);
        return ResponseEntity.noContent().build();
    }
}
