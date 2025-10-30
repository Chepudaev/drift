package com.example.drift.controller;

import com.example.drift.dto.CreateTrackConfigDto;
import com.example.drift.dto.CreateTrackDto;
import com.example.drift.dto.TrackConfigDto;
import com.example.drift.dto.TrackDto;
import com.example.drift.dto.UpdateTrackConfigDto;
import com.example.drift.service.TrackConfigService;
import com.example.drift.service.TrackService;
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
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
@Tag(name = "Треки", description = "API для управления треками и их конфигурациями")
@SecurityRequirement(name = "Bearer Authentication")
public class TrackController {

    private final TrackService trackService;
    private final TrackConfigService trackConfigService;

    // Методы для TrackEntity

    @Operation(summary = "Получить все треки", description = "Возвращает список всех треков")
    @ApiResponse(responseCode = "200", description = "Список треков")
    @GetMapping
    public ResponseEntity<List<TrackDto>> getAllTracks() {
        List<TrackDto> tracks = trackService.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @Operation(summary = "Получить трек по ID", description = "Возвращает информацию о треке по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Трек найден",
                    content = @Content(schema = @Schema(implementation = TrackDto.class))),
            @ApiResponse(responseCode = "404", description = "Трек не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TrackDto> getTrackById(@PathVariable Long id) {
        TrackDto track = trackService.getTrackById(id);
        return ResponseEntity.ok(track);
    }

    @Operation(summary = "Получить трек с конфигурациями", description = "Возвращает трек вместе со всеми его конфигурациями")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Трек с конфигурациями найден",
                    content = @Content(schema = @Schema(implementation = TrackDto.class))),
            @ApiResponse(responseCode = "404", description = "Трек не найден", content = @Content)
    })
    @GetMapping("/{id}/with-configs")
    public ResponseEntity<TrackDto> getTrackWithConfigs(@PathVariable Long id) {
        TrackDto track = trackService.getTrackWithConfigs(id);
        return ResponseEntity.ok(track);
    }

    @Operation(summary = "Создать трек", description = "Создает новый трек")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Трек создан",
                    content = @Content(schema = @Schema(implementation = TrackDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TrackDto> createTrack(@Valid @RequestBody CreateTrackDto createTrackDto) {
        TrackDto track = trackService.createTrack(createTrackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(track);
    }

    @Operation(summary = "Обновить трек", description = "Частично обновляет существующий трек")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Трек обновлен",
                    content = @Content(schema = @Schema(implementation = TrackDto.class))),
            @ApiResponse(responseCode = "404", description = "Трек не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TrackDto> updateTrack(@PathVariable Long id, @RequestBody TrackDto trackDto) {
        TrackDto updatedTrack = trackService.updateTrack(id, trackDto);
        return ResponseEntity.ok(updatedTrack);
    }

    @Operation(summary = "Удалить трек", description = "Удаляет трек по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Трек удален"),
            @ApiResponse(responseCode = "404", description = "Трек не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Скопировать трек", description = "Создает копию существующего трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Трек скопирован",
                    content = @Content(schema = @Schema(implementation = TrackDto.class))),
            @ApiResponse(responseCode = "404", description = "Исходный трек не найден", content = @Content)
    })
    @PostMapping("/{id}/copy")
    public ResponseEntity<TrackDto> copyTrack(@PathVariable Long id) {
        TrackDto copiedTrack = trackService.copyTrack(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(copiedTrack);
    }

    // Методы для TrackConfigEntity

    @Operation(summary = "Получить все конфигурации треков", description = "Возвращает список всех конфигураций треков")
    @ApiResponse(responseCode = "200", description = "Список конфигураций треков")
    @GetMapping("/configs")
    public ResponseEntity<List<TrackConfigDto>> getAllTrackConfigs() {
        List<TrackConfigDto> configs = trackConfigService.getAllTrackConfigs();
        return ResponseEntity.ok(configs);
    }

    @Operation(summary = "Получить конфигурацию трека по ID", description = "Возвращает конфигурацию трека по её ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация найдена",
                    content = @Content(schema = @Schema(implementation = TrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content)
    })
    @GetMapping("/configs/{id}")
    public ResponseEntity<TrackConfigDto> getTrackConfigById(@PathVariable Long id) {
        TrackConfigDto config = trackConfigService.getTrackConfigById(id);
        return ResponseEntity.ok(config);
    }

    @Operation(summary = "Получить все конфигурации трека по ID трека", description = "Возвращает список всех конфигураций для указанного трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список конфигураций трека"),
            @ApiResponse(responseCode = "404", description = "Трек не найден", content = @Content)
    })
    @GetMapping("/{trackId}/configs")
    public ResponseEntity<List<TrackConfigDto>> getTrackConfigsByTrackId(@PathVariable Long trackId) {
        List<TrackConfigDto> configs = trackConfigService.getTrackConfigsByTrackId(trackId);
        return ResponseEntity.ok(configs);
    }

    @Operation(summary = "Создать конфигурацию трека", description = "Создает новую конфигурацию трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Конфигурация создана",
                    content = @Content(schema = @Schema(implementation = TrackConfigDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping("/configs")
    public ResponseEntity<TrackConfigDto> createTrackConfig(@Valid @RequestBody CreateTrackConfigDto createTrackConfigDto) {
        TrackConfigDto config = trackConfigService.createTrackConfig(createTrackConfigDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(config);
    }

    @Operation(summary = "Обновить конфигурацию трека", description = "Обновляет существующую конфигурацию трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация обновлена",
                    content = @Content(schema = @Schema(implementation = TrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/configs/{id}")
    public ResponseEntity<TrackConfigDto> updateTrackConfig(@PathVariable Long id, @Valid @RequestBody TrackConfigDto trackConfigDto) {
        TrackConfigDto updatedConfig = trackConfigService.updateTrackConfig(id, trackConfigDto);
        return ResponseEntity.ok(updatedConfig);
    }

    @Operation(summary = "Частично обновить конфигурацию трека", description = "Частично обновляет поля конфигурации трека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Конфигурация обновлена",
                    content = @Content(schema = @Schema(implementation = TrackConfigDto.class))),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/configs/{id}")
    public ResponseEntity<TrackConfigDto> patchTrackConfig(@PathVariable Long id, @RequestBody UpdateTrackConfigDto updateDto) {
        TrackConfigDto updatedConfig = trackConfigService.patchTrackConfig(id, updateDto);
        return ResponseEntity.ok(updatedConfig);
    }

    @Operation(summary = "Удалить конфигурацию трека", description = "Удаляет конфигурацию трека по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Конфигурация удалена"),
            @ApiResponse(responseCode = "404", description = "Конфигурация не найдена", content = @Content)
    })
    @DeleteMapping("/configs/{id}")
    public ResponseEntity<Void> deleteTrackConfig(@PathVariable Long id) {
        trackConfigService.deleteTrackConfig(id);
        return ResponseEntity.noContent().build();
    }
}

