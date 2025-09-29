package com.example.drift.controller;

import com.example.drift.dto.CreateTrackConfigDto;
import com.example.drift.dto.CreateTrackDto;
import com.example.drift.dto.TrackConfigDto;
import com.example.drift.dto.TrackDto;
import com.example.drift.service.TrackConfigService;
import com.example.drift.service.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final TrackConfigService trackConfigService;

    // Методы для TrackEntity

    @GetMapping
    public ResponseEntity<List<TrackDto>> getAllTracks() {
        List<TrackDto> tracks = trackService.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDto> getTrackById(@PathVariable Long id) {
        TrackDto track = trackService.getTrackById(id);
        return ResponseEntity.ok(track);
    }

    @GetMapping("/{id}/with-configs")
    public ResponseEntity<TrackDto> getTrackWithConfigs(@PathVariable Long id) {
        TrackDto track = trackService.getTrackWithConfigs(id);
        return ResponseEntity.ok(track);
    }

    @PostMapping
    public ResponseEntity<TrackDto> createTrack(@Valid @RequestBody CreateTrackDto createTrackDto) {
        TrackDto track = trackService.createTrack(createTrackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(track);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrackDto> updateTrack(@PathVariable Long id, @RequestBody TrackDto trackDto) {
        TrackDto updatedTrack = trackService.updateTrack(id, trackDto);
        return ResponseEntity.ok(updatedTrack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/copy")
    public ResponseEntity<TrackDto> copyTrack(@PathVariable Long id) {
        TrackDto copiedTrack = trackService.copyTrack(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(copiedTrack);
    }

    // Методы для TrackConfigEntity

    @GetMapping("/configs")
    public ResponseEntity<List<TrackConfigDto>> getAllTrackConfigs() {
        List<TrackConfigDto> configs = trackConfigService.getAllTrackConfigs();
        return ResponseEntity.ok(configs);
    }

    @GetMapping("/configs/{id}")
    public ResponseEntity<TrackConfigDto> getTrackConfigById(@PathVariable Long id) {
        TrackConfigDto config = trackConfigService.getTrackConfigById(id);
        return ResponseEntity.ok(config);
    }

    @PostMapping("/configs")
    public ResponseEntity<TrackConfigDto> createTrackConfig(@Valid @RequestBody CreateTrackConfigDto createTrackConfigDto) {
        TrackConfigDto config = trackConfigService.createTrackConfig(createTrackConfigDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(config);
    }

    @PutMapping("/configs/{id}")
    public ResponseEntity<TrackConfigDto> updateTrackConfig(@PathVariable Long id, @Valid @RequestBody TrackConfigDto trackConfigDto) {
        TrackConfigDto updatedConfig = trackConfigService.updateTrackConfig(id, trackConfigDto);
        return ResponseEntity.ok(updatedConfig);
    }

    @DeleteMapping("/configs/{id}")
    public ResponseEntity<Void> deleteTrackConfig(@PathVariable Long id) {
        trackConfigService.deleteTrackConfig(id);
        return ResponseEntity.noContent().build();
    }
}

