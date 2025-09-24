package com.example.drift.repository;

import com.example.drift.entity.FaceToFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceToFaceRepository extends JpaRepository<FaceToFaceEntity, Long> {
}
