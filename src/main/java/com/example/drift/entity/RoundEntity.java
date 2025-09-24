package com.example.drift.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rounds")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    @Column(name = "user_id1", nullable = false)
    private Long userId1;

    @Column(name = "user_id2", nullable = false)
    private Long userId2;

    @Column(name = "user_winner_id")
    private Long userWinnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "face_to_face_id", nullable = false)
    private FaceToFaceEntity faceToFace;
}
