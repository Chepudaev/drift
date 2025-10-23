package com.example.drift.controller;

import com.example.drift.dto.CreateEventTrackConfigDto;
import com.example.drift.dto.EventTrackConfigDto;
import com.example.drift.dto.UpdateEventTrackConfigDto;
import com.example.drift.service.EventTrackConfigService;
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
@RequestMapping("/api/event-track-configs")
@RequiredArgsConstructor
@Tag(name = "Конфигурации событий и трасс", description = "API для управления связями событий с трассами и их конфигурациями")
@SecurityRequirement(name = "Bearer Authentication")
public class EventTrackConfigController {

    private final EventTrackConfigService eventTrackConfigService;

    @Operation(summary = "Получить все конфигурации событий", description = "Возвращает список всех конфигураций событий и трасс")
    @ApiResponse(responseCode = "200", description = "Список конфигураций")
    @GetMapping
    public ResponseEntity<List<EventTrackConfigDto>> getAllEventTrackConfigs() {
        List<EventTrackConfigDto> configs = eventTrackConfigService.getAllEventTrackConfigs();
        return ResponseEntity.ok(configs);
    }

    @Operation(summary = "Получить конфигурацию по ID", description = "Возвращает информацию о конфигурации по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация найдена",
                    content = @Content(schema = @Schema(implementation = EventTrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventTrackConfigDto> getEventTrackConfigById(@PathVariable Long id) {
        EventTrackConfigDto config = eventTrackConfigService.getEventTrackConfigById(id);
        return ResponseEntity.ok(config);
    }

    @Operation(summary = "Получить конфигурацию по ID события", description = "Возвращает конфигурацию для конкретного события")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация найдена",
                    content = @Content(schema = @Schema(implementation = EventTrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content)
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventTrackConfigDto> getEventTrackConfigByEventId(@PathVariable Long eventId) {
        EventTrackConfigDto config = eventTrackConfigService.getEventTrackConfigByEventId(eventId);
        return ResponseEntity.ok(config);
    }

    @Operation(summary = "Создать конфигурацию", description = "Создает новую конфигурацию события и трассы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Конфигурация создана",
                    content = @Content(schema = @Schema(implementation = EventTrackConfigDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventTrackConfigDto> createEventTrackConfig(@Valid @RequestBody CreateEventTrackConfigDto createDto) {
        EventTrackConfigDto config = eventTrackConfigService.createEventTrackConfig(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(config);
    }

    @Operation(summary = "Обновить конфигурацию", description = "Частично обновляет существующую конфигурацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация обновлена",
                    content = @Content(schema = @Schema(implementation = EventTrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EventTrackConfigDto> updateEventTrackConfig(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventTrackConfigDto updateDto) {
        EventTrackConfigDto updatedConfig = eventTrackConfigService.updateEventTrackConfig(id, updateDto);
        return ResponseEntity.ok(updatedConfig);
    }

    @Operation(summary = "Удалить конфигурацию", description = "Удаляет конфигурацию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Конфигурация удалена"),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventTrackConfig(@PathVariable Long id) {
        eventTrackConfigService.deleteEventTrackConfig(id);
        return ResponseEntity.noContent().build();
    }
}




