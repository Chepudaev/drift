package com.example.drift.repository;

import com.example.drift.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.cars")
    List<UserEntity> findAllWithCars();

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.cars WHERE u.id = :id")
    Optional<UserEntity> findByIdWithCars(@Param("id") Long id);
}
