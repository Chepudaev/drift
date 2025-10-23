package com.example.drift.repository;

import com.example.drift.entity.EventTrackConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTrackConfigRepository extends JpaRepository<EventTrackConfigEntity, Long> {
    Optional<EventTrackConfigEntity> findByEventId(Long eventId);
}




