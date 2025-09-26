package com.example.drift.repository;

import com.example.drift.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT s FROM ScheduleEntity s LEFT JOIN FETCH s.scheduleElements")
    List<ScheduleEntity> findAllWithElements();

    @Query("SELECT s FROM ScheduleEntity s LEFT JOIN FETCH s.scheduleElements WHERE s.id = :id")
    Optional<ScheduleEntity> findByIdWithElements(@Param("id") Long id);

    boolean existsByName(String name);
}
