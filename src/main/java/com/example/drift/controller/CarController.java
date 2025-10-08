package com.example.drift.controller;

import com.example.drift.dto.CarDto;
import com.example.drift.dto.CreateCarDto;
import com.example.drift.service.CarService;
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
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили", description = "API для управления автомобилями")
@SecurityRequirement(name = "Bearer Authentication")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Получить все автомобили", description = "Возвращает список всех автомобилей")
    @ApiResponse(responseCode = "200", description = "Список автомобилей")
    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Получить автомобиль по ID", description = "Возвращает информацию об автомобиле по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль найден",
                    content = @Content(schema = @Schema(implementation = CarDto.class))),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        CarDto car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Получить автомобили пользователя", description = "Возвращает список автомобилей конкретного пользователя")
    @ApiResponse(responseCode = "200", description = "Список автомобилей пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CarDto>> getCarsByUserId(@PathVariable Long userId) {
        List<CarDto> cars = carService.getCarsByUserId(userId);
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Создать автомобиль", description = "Создает новый автомобиль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Автомобиль создан",
                    content = @Content(schema = @Schema(implementation = CarDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CreateCarDto createCarDto) {
        CarDto createdCar = carService.createCar(createCarDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @Operation(summary = "Обновить автомобиль", description = "Обновляет существующий автомобиль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль обновлен",
                    content = @Content(schema = @Schema(implementation = CarDto.class))),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content),
            @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @Valid @RequestBody CreateCarDto updateCarDto) {
        CarDto updatedCar = carService.updateCar(id, updateCarDto);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Удалить автомобиль", description = "Удаляет автомобиль по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Автомобиль удален"),
            @ApiResponse(responseCode = "404", description = "Автомобиль не найден", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}



