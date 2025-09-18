package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    @NotBlank(message = "Марка автомобиля обязательна для заполнения")
    private String brand;

    @Column(name = "model", nullable = false)
    @NotBlank(message = "Модель автомобиля обязательна для заполнения")
    private String model;

    @Column(name = "horsepower", nullable = false)
    @NotNull(message = "Лошадиные силы обязательны для заполнения")
    @Min(value = 1, message = "Лошадиные силы должны быть больше 0")
    private Integer horsepower;

    @Column(name = "user_photo_url")
    private String userPhotoUrl;

    @Column(name = "moderator_photo_url")
    private String moderatorPhotoUrl;

    @Column(name = "year", nullable = false)
    @NotNull(message = "Год автомобиля обязателен для заполнения")
    @Min(value = 1900, message = "Год должен быть не раньше 1900")
    private Integer year;

    @Column(name = "color")
    private String color;

    @Column(name = "car_class")
    private String carClass;

    @Column(name = "color1", nullable = false)
    @NotBlank(message = "Цвет1 обязателен для заполнения")
    private String color1;

    @Column(name = "color2")
    private String color2;

    @Column(name = "color3")
    private String color3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
