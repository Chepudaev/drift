package com.example.drift.service;

import com.example.drift.dto.CreateRoundDto;
import com.example.drift.dto.RoundDto;
import com.example.drift.entity.FaceToFaceEntity;
import com.example.drift.entity.RoundEntity;
import com.example.drift.exception.FaceToFaceNotFoundException;
import com.example.drift.exception.RoundNotFoundException;
import com.example.drift.mapper.FaceToFaceMapper;
import com.example.drift.repository.FaceToFaceRepository;
import com.example.drift.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoundService {

    private final RoundRepository roundRepository;
    private final FaceToFaceRepository faceToFaceRepository;
    private final FaceToFaceMapper faceToFaceMapper;

    @Transactional(readOnly = true)
    public List<RoundDto> getAllRounds() {
        List<RoundEntity> rounds = roundRepository.findAll();
        return faceToFaceMapper.toRoundDtoList(rounds);
    }

    @Transactional(readOnly = true)
    public RoundDto getRoundById(Long id) {
        RoundEntity round = roundRepository.findById(id)
                .orElseThrow(() -> new RoundNotFoundException("Раунд с ID " + id + " не найден"));
        return faceToFaceMapper.toRoundDto(round);
    }

    public RoundDto createRound(CreateRoundDto createRoundDto) {
        // Проверяем, что FaceToFace существует
        FaceToFaceEntity faceToFace = faceToFaceRepository.findById(createRoundDto.getFaceToFaceId())
                .orElseThrow(() -> new FaceToFaceNotFoundException("FaceToFace с ID " + createRoundDto.getFaceToFaceId() + " не найдена"));

        RoundEntity round = faceToFaceMapper.toRoundEntity(createRoundDto);
        round.setFaceToFace(faceToFace);
        
        RoundEntity savedRound = roundRepository.save(round);
        return faceToFaceMapper.toRoundDto(savedRound);
    }

    public RoundDto updateRound(Long id, RoundDto roundDto) {
        RoundEntity existingRound = roundRepository.findById(id)
                .orElseThrow(() -> new RoundNotFoundException("Раунд с ID " + id + " не найден"));

        // Обновляем поля
        if (roundDto.getRoundNumber() != null) {
            existingRound.setRoundNumber(roundDto.getRoundNumber());
        }
        if (roundDto.getUserId1() != null) {
            existingRound.setUserId1(roundDto.getUserId1());
        }
        if (roundDto.getUserId2() != null) {
            existingRound.setUserId2(roundDto.getUserId2());
        }
        if (roundDto.getUserWinnerId() != null) {
            existingRound.setUserWinnerId(roundDto.getUserWinnerId());
        }

        // Если указан новый faceToFaceId, проверяем его существование и обновляем связь
        if (roundDto.getFaceToFaceId() != null) {
            FaceToFaceEntity faceToFace = faceToFaceRepository.findById(roundDto.getFaceToFaceId())
                    .orElseThrow(() -> new FaceToFaceNotFoundException("FaceToFace с ID " + roundDto.getFaceToFaceId() + " не найдена"));
            existingRound.setFaceToFace(faceToFace);
        }

        RoundEntity updatedRound = roundRepository.save(existingRound);
        return faceToFaceMapper.toRoundDto(updatedRound);
    }

    public void deleteRound(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new RoundNotFoundException("Раунд с ID " + id + " не найден");
        }
        roundRepository.deleteById(id);
    }
}
