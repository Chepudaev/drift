package com.example.drift.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDto {

    private Long id;

    @NotBlank(message = "Марка автомобиля обязательна для заполнения")
    private String brand;

    @NotBlank(message = "Модель автомобиля обязательна для заполнения")
    private String model;

    @NotNull(message = "Лошадиные силы обязательны для заполнения")
    @Min(value = 1, message = "Лошадиные силы должны быть больше 0")
    private Integer horsepower;

    private String userPhotoUrl;

    private String moderatorPhotoUrl;

    @NotNull(message = "Год автомобиля обязателен для заполнения")
    @Min(value = 1900, message = "Год должен быть не раньше 1900")
    private Integer year;

    private String color;

    private String carClass;

    @NotBlank(message = "Цвет1 обязателен для заполнения")
    private String color1;

    private String color2;

    private String color3;

    private Long userId;

    private Set<String> tyreClasses;
}
