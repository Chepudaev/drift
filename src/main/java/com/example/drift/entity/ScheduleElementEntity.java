package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule_elements")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleElementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    @NotBlank(message = "Время начала обязательно для заполнения")
    @Pattern(regexp = "^(1[0-2]|[1-9]):[0-5][0-9](AM|PM)$", 
             message = "Время должно быть в формате HH:MMAM или HH:MMPM (например: 10:40AM или 10:50PM)")
    private String startTime;

    @Column(name = "end_time", nullable = false)
    @NotBlank(message = "Время окончания обязательно для заполнения")
    @Pattern(regexp = "^(1[0-2]|[1-9]):[0-5][0-9](AM|PM)$", 
             message = "Время должно быть в формате HH:MMAM или HH:MMPM (например: 10:40AM или 10:50PM)")
    private String endTime;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Описание обязательно для заполнения")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ScheduleEntity schedule;

}
