package com.example.drift.service;

import com.example.drift.dto.CreateFaceToFaceDto;
import com.example.drift.dto.FaceToFaceDto;
import com.example.drift.entity.CarEntity;
import com.example.drift.entity.FaceToFaceEntity;
import com.example.drift.entity.UserEntity;
import com.example.drift.exception.FaceToFaceNotFoundException;
import com.example.drift.mapper.FaceToFaceMapper;
import com.example.drift.repository.CarRepository;
import com.example.drift.repository.FaceToFaceRepository;
import com.example.drift.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FaceToFaceService {

    private final FaceToFaceRepository faceToFaceRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final FaceToFaceMapper faceToFaceMapper;

    @Transactional(readOnly = true)
    public List<FaceToFaceDto> getAllFaceToFace() {
        List<FaceToFaceEntity> faceToFaceList = faceToFaceRepository.findAll();
        return faceToFaceMapper.toDtoList(faceToFaceList);
    }

    @Transactional(readOnly = true)
    public FaceToFaceDto getFaceToFaceById(Long id) {
        FaceToFaceEntity faceToFace = faceToFaceRepository.findById(id)
                .orElseThrow(() -> new FaceToFaceNotFoundException("FaceToFace с ID " + id + " не найдена"));
        return faceToFaceMapper.toDto(faceToFace);
    }

    @Transactional(readOnly = true)
    public FaceToFaceDto getFaceToFaceWithRounds(Long id) {
        FaceToFaceEntity faceToFace = faceToFaceRepository.findById(id)
                .orElseThrow(() -> new FaceToFaceNotFoundException("FaceToFace с ID " + id + " не найдена"));
        
        // Загружаем раунды
        faceToFace.getRounds().size(); // Инициируем загрузку
        
        return faceToFaceMapper.toDtoWithRounds(faceToFace);
    }

    public FaceToFaceDto createFaceToFace(CreateFaceToFaceDto createFaceToFaceDto) {
        FaceToFaceEntity faceToFace = faceToFaceMapper.toEntity(createFaceToFaceDto);
        
        // Устанавливаем связанные сущности если указаны ID
        if (createFaceToFaceDto.getUser1Id() != null) {
            UserEntity user1 = userRepository.findById(createFaceToFaceDto.getUser1Id())
                    .orElseThrow(() -> new RuntimeException("Пользователь с ID " + createFaceToFaceDto.getUser1Id() + " не найден"));
            faceToFace.setUser1(user1);
        }
        
        if (createFaceToFaceDto.getUser2Id() != null) {
            UserEntity user2 = userRepository.findById(createFaceToFaceDto.getUser2Id())
                    .orElseThrow(() -> new RuntimeException("Пользователь с ID " + createFaceToFaceDto.getUser2Id() + " не найден"));
            faceToFace.setUser2(user2);
        }
        
        if (createFaceToFaceDto.getAutoUser1Id() != null) {
            CarEntity autoUser1 = carRepository.findById(createFaceToFaceDto.getAutoUser1Id())
                    .orElseThrow(() -> new RuntimeException("Автомобиль с ID " + createFaceToFaceDto.getAutoUser1Id() + " не найден"));
            faceToFace.setAutoUser1(autoUser1);
        }
        
        if (createFaceToFaceDto.getAutoUser2Id() != null) {
            CarEntity autoUser2 = carRepository.findById(createFaceToFaceDto.getAutoUser2Id())
                    .orElseThrow(() -> new RuntimeException("Автомобиль с ID " + createFaceToFaceDto.getAutoUser2Id() + " не найден"));
            faceToFace.setAutoUser2(autoUser2);
        }
        
        FaceToFaceEntity savedFaceToFace = faceToFaceRepository.save(faceToFace);
        return faceToFaceMapper.toDto(savedFaceToFace);
    }

    public FaceToFaceDto updateFaceToFace(Long id, FaceToFaceDto faceToFaceDto) {
        FaceToFaceEntity existingFaceToFace = faceToFaceRepository.findById(id)
                .orElseThrow(() -> new FaceToFaceNotFoundException("FaceToFace с ID " + id + " не найдена"));

        // Обновляем только переданные поля
        if (faceToFaceDto.getStartTime() != null) {
            existingFaceToFace.setStartTime(faceToFaceDto.getStartTime());
        }
        if (faceToFaceDto.getUserPhoto1() != null) {
            existingFaceToFace.setUserPhoto1(faceToFaceDto.getUserPhoto1());
        }
        if (faceToFaceDto.getUserPhoto2() != null) {
            existingFaceToFace.setUserPhoto2(faceToFaceDto.getUserPhoto2());
        }
        if (faceToFaceDto.getAutoPhoto1() != null) {
            existingFaceToFace.setAutoPhoto1(faceToFaceDto.getAutoPhoto1());
        }
        if (faceToFaceDto.getAutoPhoto2() != null) {
            existingFaceToFace.setAutoPhoto2(faceToFaceDto.getAutoPhoto2());
        }

        // Обновляем связанные сущности если указаны ID
        if (faceToFaceDto.getUser1Id() != null) {
            UserEntity user1 = userRepository.findById(faceToFaceDto.getUser1Id())
                    .orElseThrow(() -> new RuntimeException("Пользователь с ID " + faceToFaceDto.getUser1Id() + " не найден"));
            existingFaceToFace.setUser1(user1);
        }
        
        if (faceToFaceDto.getUser2Id() != null) {
            UserEntity user2 = userRepository.findById(faceToFaceDto.getUser2Id())
                    .orElseThrow(() -> new RuntimeException("Пользователь с ID " + faceToFaceDto.getUser2Id() + " не найден"));
            existingFaceToFace.setUser2(user2);
        }
        
        if (faceToFaceDto.getAutoUser1Id() != null) {
            CarEntity autoUser1 = carRepository.findById(faceToFaceDto.getAutoUser1Id())
                    .orElseThrow(() -> new RuntimeException("Автомобиль с ID " + faceToFaceDto.getAutoUser1Id() + " не найден"));
            existingFaceToFace.setAutoUser1(autoUser1);
        }
        
        if (faceToFaceDto.getAutoUser2Id() != null) {
            CarEntity autoUser2 = carRepository.findById(faceToFaceDto.getAutoUser2Id())
                    .orElseThrow(() -> new RuntimeException("Автомобиль с ID " + faceToFaceDto.getAutoUser2Id() + " не найден"));
            existingFaceToFace.setAutoUser2(autoUser2);
        }

        FaceToFaceEntity updatedFaceToFace = faceToFaceRepository.save(existingFaceToFace);
        return faceToFaceMapper.toDto(updatedFaceToFace);
    }

    public void deleteFaceToFace(Long id) {
        if (!faceToFaceRepository.existsById(id)) {
            throw new FaceToFaceNotFoundException("FaceToFace с ID " + id + " не найдена");
        }
        faceToFaceRepository.deleteById(id);
    }
}
