package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Дата события обязательна для заполнения")
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    @NotNull(message = "Трасса обязательна для заполнения")
    private TrackEntity track;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FaceToFaceEntity> faceToFaceEntities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    @NotNull(message = "Расписание обязательно для заполнения")
    private ScheduleEntity schedule;

    @Column(name = "driver_limit", nullable = false)
    @Min(value = 1, message = "Лимит водителей должен быть не менее 1")
    @NotNull(message = "Лимит водителей обязателен для заполнения")
    private Integer driverLimit;

    @Column(name = "spectator_limit", nullable = false)
    @Min(value = 0, message = "Лимит зрителей должен быть не менее 0")
    @NotNull(message = "Лимит зрителей обязателен для заполнения")
    private Integer spectatorLimit;

    @Column(name = "event_type", nullable = false)
    @NotBlank(message = "Тип события обязателен для заполнения")
    private String eventType;
}
