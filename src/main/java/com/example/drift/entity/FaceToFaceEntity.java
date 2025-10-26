package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "face_to_face")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FaceToFaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "user_photo1")
    private String userPhoto1;

    @Column(name = "user_photo2")
    private String userPhoto2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private UserEntity user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private UserEntity user2;

    @Column(name = "auto_photo1")
    private String autoPhoto1;

    @Column(name = "auto_photo2")
    private String autoPhoto2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_user1_id")
    private CarEntity autoUser1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_user2_id")
    private CarEntity autoUser2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @OneToMany(mappedBy = "faceToFace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoundEntity> rounds;
}
