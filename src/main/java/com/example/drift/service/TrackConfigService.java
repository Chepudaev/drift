package com.example.drift.service;

import com.example.drift.dto.CreateTrackConfigDto;
import com.example.drift.dto.TrackConfigDto;
import com.example.drift.dto.UpdateTrackConfigDto;
import com.example.drift.entity.TrackConfigEntity;
import com.example.drift.entity.TrackEntity;
import com.example.drift.exception.TrackConfigNotFoundException;
import com.example.drift.exception.TrackNotFoundException;
import com.example.drift.mapper.TrackMapper;
import com.example.drift.repository.TrackConfigRepository;
import com.example.drift.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackConfigService {

    private final TrackConfigRepository trackConfigRepository;
    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    @Transactional(readOnly = true)
    public List<TrackConfigDto> getAllTrackConfigs() {
        List<TrackConfigEntity> configs = trackConfigRepository.findAll();
        return trackMapper.toConfigDtoList(configs);
    }

    @Transactional(readOnly = true)
    public TrackConfigDto getTrackConfigById(Long id) {
        TrackConfigEntity config = trackConfigRepository.findById(id)
                .orElseThrow(() -> new TrackConfigNotFoundException("Конфигурация трассы с ID " + id + " не найдена"));
        return trackMapper.toDto(config);
    }

    @Transactional(readOnly = true)
    public List<TrackConfigDto> getTrackConfigsByTrackId(Long trackId) {
        // Проверяем, что трек существует
        if (!trackRepository.existsById(trackId)) {
            throw new TrackNotFoundException("Трасса с ID " + trackId + " не найдена");
        }
        
        List<TrackConfigEntity> configs = trackConfigRepository.findByTrackId(trackId);
        return trackMapper.toConfigDtoList(configs);
    }

    public TrackConfigDto createTrackConfig(CreateTrackConfigDto createTrackConfigDto) {
        // Проверяем, что трасса существует
        TrackEntity track = trackRepository.findById(createTrackConfigDto.getTrackId())
                .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + createTrackConfigDto.getTrackId() + " не найдена"));

        TrackConfigEntity config = trackMapper.toEntity(createTrackConfigDto);
        config.setTrack(track);
        
        TrackConfigEntity savedConfig = trackConfigRepository.save(config);
        return trackMapper.toDto(savedConfig);
    }

    public TrackConfigDto updateTrackConfig(Long id, TrackConfigDto trackConfigDto) {
        TrackConfigEntity existingConfig = trackConfigRepository.findById(id)
                .orElseThrow(() -> new TrackConfigNotFoundException("Конфигурация трассы с ID " + id + " не найдена"));

        // Обновляем конфигурацию
        existingConfig.setConfig(trackConfigDto.getConfig());
        existingConfig.setMainMapConfigMobileUrl(trackConfigDto.getMainMapConfigMobileUrl());
        existingConfig.setMainMapConfigWebUrl(trackConfigDto.getMainMapConfigWebUrl());

        // Если указан новый trackId, проверяем его существование и обновляем связь
        if (trackConfigDto.getTrackId() != null) {
            TrackEntity track = trackRepository.findById(trackConfigDto.getTrackId())
                    .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + trackConfigDto.getTrackId() + " не найдена"));
            existingConfig.setTrack(track);
        }

        TrackConfigEntity updatedConfig = trackConfigRepository.saveAndFlush(existingConfig);
        return trackMapper.toDto(updatedConfig);
    }

    public void deleteTrackConfig(Long id) {
        if (!trackConfigRepository.existsById(id)) {
            throw new TrackConfigNotFoundException("Конфигурация трассы с ID " + id + " не найдена");
        }
        trackConfigRepository.deleteById(id);
    }

    @Transactional
    public TrackConfigDto patchTrackConfig(Long id, UpdateTrackConfigDto updateDto) {
        TrackConfigEntity existingConfig = trackConfigRepository.findById(id)
                .orElseThrow(() -> new TrackConfigNotFoundException("Конфигурация трассы с ID " + id + " не найдена"));

        if (updateDto.getConfig() != null) {
            existingConfig.setConfig(updateDto.getConfig());
        }
        if (updateDto.getMainMapConfigMobileUrl() != null) {
            existingConfig.setMainMapConfigMobileUrl(updateDto.getMainMapConfigMobileUrl());
        }
        if (updateDto.getMainMapConfigWebUrl() != null) {
            existingConfig.setMainMapConfigWebUrl(updateDto.getMainMapConfigWebUrl());
        }
        if (updateDto.getTrackId() != null) {
            TrackEntity track = trackRepository.findById(updateDto.getTrackId())
                    .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + updateDto.getTrackId() + " не найдена"));
            existingConfig.setTrack(track);
        }

        TrackConfigEntity saved = trackConfigRepository.saveAndFlush(existingConfig);
        return trackMapper.toDto(saved);
    }
}








