package com.example.drift.controller;

import com.example.drift.dto.CreateFaceToFaceDto;
import com.example.drift.dto.FaceToFaceDto;
import com.example.drift.service.FaceToFaceService;
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
@RequestMapping("/api/face-to-face")
@RequiredArgsConstructor
@Tag(name = "Face-to-Face соревнования", description = "API для управления прямыми соревнованиями один на один")
@SecurityRequirement(name = "Bearer Authentication")
public class FaceToFaceController {

    private final FaceToFaceService faceToFaceService;

    @Operation(summary = "Получить все Face-to-Face соревнования", description = "Возвращает список всех прямых соревнований")
    @ApiResponse(responseCode = "200", description = "Список Face-to-Face соревнований")
    @GetMapping
    public ResponseEntity<List<FaceToFaceDto>> getAllFaceToFace() {
        List<FaceToFaceDto> faceToFaceList = faceToFaceService.getAllFaceToFace();
        return ResponseEntity.ok(faceToFaceList);
    }

    @Operation(summary = "Получить Face-to-Face соревнование по ID", description = "Возвращает информацию о соревновании по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Соревнование найдено",
                    content = @Content(schema = @Schema(implementation = FaceToFaceDto.class))),
            @ApiResponse(responseCode = "404", description = "Соревнование не найдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaceToFaceDto> getFaceToFaceById(@PathVariable Long id) {
        FaceToFaceDto faceToFace = faceToFaceService.getFaceToFaceById(id);
        return ResponseEntity.ok(faceToFace);
    }

    @Operation(summary = "Получить Face-to-Face с раундами", description = "Возвращает соревнование вместе со всеми его раундами")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Соревнование с раундами найдено",
                    content = @Content(schema = @Schema(implementation = FaceToFaceDto.class))),
            @ApiResponse(responseCode = "404", description = "Соревнование не найдено", content = @Content)
    })
    @GetMapping("/{id}/with-rounds")
    public ResponseEntity<FaceToFaceDto> getFaceToFaceWithRounds(@PathVariable Long id) {
        FaceToFaceDto faceToFace = faceToFaceService.getFaceToFaceWithRounds(id);
        return ResponseEntity.ok(faceToFace);
    }

    @Operation(
            summary = "Создать Face-to-Face соревнование", 
            description = "Создает новое прямое соревнование. Обязательное поле: eventId. Все остальные поля опциональны."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Соревнование создано",
                    content = @Content(schema = @Schema(implementation = FaceToFaceDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные (например, отсутствует eventId)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<FaceToFaceDto> createFaceToFace(@Valid @RequestBody CreateFaceToFaceDto createFaceToFaceDto) {
        FaceToFaceDto faceToFace = faceToFaceService.createFaceToFace(createFaceToFaceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(faceToFace);
    }

    @Operation(summary = "Обновить Face-to-Face соревнование", description = "Частично обновляет существующее соревнование")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Соревнование обновлено",
                    content = @Content(schema = @Schema(implementation = FaceToFaceDto.class))),
            @ApiResponse(responseCode = "404", description = "Соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<FaceToFaceDto> updateFaceToFace(@PathVariable Long id, @RequestBody FaceToFaceDto faceToFaceDto) {
        FaceToFaceDto updatedFaceToFace = faceToFaceService.updateFaceToFace(id, faceToFaceDto);
        return ResponseEntity.ok(updatedFaceToFace);
    }

    @Operation(summary = "Удалить Face-to-Face соревнование", description = "Удаляет соревнование по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Соревнование удалено"),
            @ApiResponse(responseCode = "404", description = "Соревнование не найдено", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaceToFace(@PathVariable Long id) {
        faceToFaceService.deleteFaceToFace(id);
        return ResponseEntity.noContent().build();
    }
}
