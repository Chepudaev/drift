package com.example.drift.controller;

import com.example.drift.dto.CreateEventDto;
import com.example.drift.dto.EventDto;
import com.example.drift.service.EventService;
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
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "События", description = "API для управления дрифт-событиями")
@SecurityRequirement(name = "Bearer Authentication")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Получить все события", description = "Возвращает список всех дрифт-событий")
    @ApiResponse(responseCode = "200", description = "Список событий")
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Получить событие по ID", description = "Возвращает информацию о событии по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Событие найдено",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "404", description = "Событие не найдено", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @Operation(summary = "Создать событие", description = "Создает новое дрифт-событие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Событие создано",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody CreateEventDto createEventDto) {
        EventDto event = eventService.createEvent(createEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @Operation(summary = "Обновить событие", description = "Частично обновляет существующее событие")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Событие обновлено",
                    content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "404", description = "Событие не найдено", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        EventDto updatedEvent = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @Operation(summary = "Удалить событие", description = "Удаляет событие по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Событие удалено"),
            @ApiResponse(responseCode = "404", description = "Событие не найдено", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

