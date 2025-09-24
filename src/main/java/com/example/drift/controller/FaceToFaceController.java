package com.example.drift.controller;

import com.example.drift.dto.CreateFaceToFaceDto;
import com.example.drift.dto.FaceToFaceDto;
import com.example.drift.service.FaceToFaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/face-to-face")
@RequiredArgsConstructor
public class FaceToFaceController {

    private final FaceToFaceService faceToFaceService;

    @GetMapping
    public ResponseEntity<List<FaceToFaceDto>> getAllFaceToFace() {
        List<FaceToFaceDto> faceToFaceList = faceToFaceService.getAllFaceToFace();
        return ResponseEntity.ok(faceToFaceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaceToFaceDto> getFaceToFaceById(@PathVariable Long id) {
        FaceToFaceDto faceToFace = faceToFaceService.getFaceToFaceById(id);
        return ResponseEntity.ok(faceToFace);
    }

    @GetMapping("/{id}/with-rounds")
    public ResponseEntity<FaceToFaceDto> getFaceToFaceWithRounds(@PathVariable Long id) {
        FaceToFaceDto faceToFace = faceToFaceService.getFaceToFaceWithRounds(id);
        return ResponseEntity.ok(faceToFace);
    }

    @PostMapping
    public ResponseEntity<FaceToFaceDto> createFaceToFace(@Valid @RequestBody CreateFaceToFaceDto createFaceToFaceDto) {
        FaceToFaceDto faceToFace = faceToFaceService.createFaceToFace(createFaceToFaceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(faceToFace);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FaceToFaceDto> updateFaceToFace(@PathVariable Long id, @RequestBody FaceToFaceDto faceToFaceDto) {
        FaceToFaceDto updatedFaceToFace = faceToFaceService.updateFaceToFace(id, faceToFaceDto);
        return ResponseEntity.ok(updatedFaceToFace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaceToFace(@PathVariable Long id) {
        faceToFaceService.deleteFaceToFace(id);
        return ResponseEntity.noContent().build();
    }
}
