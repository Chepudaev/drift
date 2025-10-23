package com.example.drift.repository;

import com.example.drift.entity.TrackConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackConfigRepository extends JpaRepository<TrackConfigEntity, Long> {
    
    /**
     * Найти все конфигурации трека по ID трека
     * @param trackId ID трека
     * @return список конфигураций трека
     */
    List<TrackConfigEntity> findByTrackId(Long trackId);
}








