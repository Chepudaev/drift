package com.example.drift.repository;

import com.example.drift.entity.ScheduleElementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleElementRepository extends JpaRepository<ScheduleElementEntity, Long> {

    List<ScheduleElementEntity> findByScheduleId(Long scheduleId);

    @Query("SELECT se FROM ScheduleElementEntity se WHERE se.schedule.id = :scheduleId ORDER BY se.startTime")
    List<ScheduleElementEntity> findByScheduleIdOrderByStartTime(@Param("scheduleId") Long scheduleId);

    void deleteByScheduleId(Long scheduleId);
}
