package com.example.drift.controller;

import com.example.drift.dto.CreateRoundDto;
import com.example.drift.dto.RoundDto;
import com.example.drift.service.RoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rounds")
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    @GetMapping
    public ResponseEntity<List<RoundDto>> getAllRounds() {
        List<RoundDto> rounds = roundService.getAllRounds();
        return ResponseEntity.ok(rounds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoundDto> getRoundById(@PathVariable Long id) {
        RoundDto round = roundService.getRoundById(id);
        return ResponseEntity.ok(round);
    }

    @PostMapping
    public ResponseEntity<RoundDto> createRound(@Valid @RequestBody CreateRoundDto createRoundDto) {
        RoundDto round = roundService.createRound(createRoundDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(round);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoundDto> updateRound(@PathVariable Long id, @Valid @RequestBody RoundDto roundDto) {
        RoundDto updatedRound = roundService.updateRound(id, roundDto);
        return ResponseEntity.ok(updatedRound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        roundService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }
}
