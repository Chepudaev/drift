package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tracks")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state", nullable = false)
    @NotBlank(message = "Штат обязателен для заполнения")
    private String state;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Адрес трассы обязателен для заполнения")
    private String address;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "instruction_url")
    private String instructionUrl;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrackConfigEntity> trackConfigs;
}
