package com.example.drift.controller;

import com.example.drift.dto.CreateRoundDto;
import com.example.drift.dto.RoundDto;
import com.example.drift.service.RoundService;
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
@RequestMapping("/api/rounds")
@RequiredArgsConstructor
@Tag(name = "Раунды", description = "API для управления раундами соревнований")
@SecurityRequirement(name = "Bearer Authentication")
public class RoundController {

    private final RoundService roundService;

    @Operation(summary = "Получить все раунды", description = "Возвращает список всех раундов")
    @ApiResponse(responseCode = "200", description = "Список раундов")
    @GetMapping
    public ResponseEntity<List<RoundDto>> getAllRounds() {
        List<RoundDto> rounds = roundService.getAllRounds();
        return ResponseEntity.ok(rounds);
    }

    @Operation(summary = "Получить раунд по ID", description = "Возвращает информацию о раунде по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Раунд найден",
                    content = @Content(schema = @Schema(implementation = RoundDto.class))),
            @ApiResponse(responseCode = "404", description = "Раунд не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoundDto> getRoundById(@PathVariable Long id) {
        RoundDto round = roundService.getRoundById(id);
        return ResponseEntity.ok(round);
    }

    @Operation(summary = "Создать раунд", description = "Создает новый раунд соревнования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Раунд создан",
                    content = @Content(schema = @Schema(implementation = RoundDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RoundDto> createRound(@Valid @RequestBody CreateRoundDto createRoundDto) {
        RoundDto round = roundService.createRound(createRoundDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(round);
    }

    @Operation(summary = "Обновить раунд", description = "Обновляет существующий раунд")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Раунд обновлен",
                    content = @Content(schema = @Schema(implementation = RoundDto.class))),
            @ApiResponse(responseCode = "404", description = "Раунд не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoundDto> updateRound(@PathVariable Long id, @Valid @RequestBody RoundDto roundDto) {
        RoundDto updatedRound = roundService.updateRound(id, roundDto);
        return ResponseEntity.ok(updatedRound);
    }

    @Operation(summary = "Удалить раунд", description = "Удаляет раунд по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Раунд удален"),
            @ApiResponse(responseCode = "404", description = "Раунд не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        roundService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }
}
