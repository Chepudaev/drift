package com.example.drift.repository;

import com.example.drift.entity.RoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long> {
}
