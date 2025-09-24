package com.example.drift.repository;

import com.example.drift.entity.TrackConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackConfigRepository extends JpaRepository<TrackConfigEntity, Long> {
}

