package com.example.drift.repository;

import com.example.drift.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    
    List<CarEntity> findByUserId(Long userId);
    
    @Query("SELECT c FROM CarEntity c JOIN FETCH c.user WHERE c.user.id = :userId")
    List<CarEntity> findByUserIdWithUser(@Param("userId") Long userId);
    
    @Query("SELECT c FROM CarEntity c JOIN FETCH c.user")
    List<CarEntity> findAllWithUser();
    
    boolean existsByIdAndUserId(Long carId, Long userId);
}