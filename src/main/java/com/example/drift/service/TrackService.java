package com.example.drift.service;

import com.example.drift.dto.CreateTrackDto;
import com.example.drift.dto.TrackDto;
import com.example.drift.entity.TrackEntity;
import com.example.drift.exception.TrackNotFoundException;
import com.example.drift.mapper.TrackMapper;
import com.example.drift.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    @Transactional(readOnly = true)
    public List<TrackDto> getAllTracks() {
        List<TrackEntity> tracks = trackRepository.findAll();
        return trackMapper.toDtoList(tracks);
    }

    @Transactional(readOnly = true)
    public TrackDto getTrackById(Long id) {
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + id + " не найдена"));
        return trackMapper.toDto(track);
    }

    @Transactional(readOnly = true)
    public TrackDto getTrackWithConfigs(Long id) {
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + id + " не найдена"));
        
        // Загружаем конфигурации трассы
        track.getTrackConfigs().size(); // Инициируем загрузку
        
        return trackMapper.toDtoWithConfigs(track);
    }

    public TrackDto createTrack(CreateTrackDto createTrackDto) {
        TrackEntity track = trackMapper.toEntity(createTrackDto);
        TrackEntity savedTrack = trackRepository.save(track);
        return trackMapper.toDto(savedTrack);
    }

    public TrackDto updateTrack(Long id, TrackDto trackDto) {
        TrackEntity existingTrack = trackRepository.findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + id + " не найдена"));

        // Обновляем только переданные поля
        if (trackDto.getState() != null) {
            existingTrack.setState(trackDto.getState());
        }
        if (trackDto.getAddress() != null) {
            existingTrack.setAddress(trackDto.getAddress());
        }
        if (trackDto.getThumbnailUrl() != null) {
            existingTrack.setThumbnailUrl(trackDto.getThumbnailUrl());
        }
        if (trackDto.getInstructionUrl() != null) {
            existingTrack.setInstructionUrl(trackDto.getInstructionUrl());
        }
        if (trackDto.getNotes() != null) {
            existingTrack.setNotes(trackDto.getNotes());
        }
        if (trackDto.getMainMapMobileUrl() != null) {
            existingTrack.setMainMapMobileUrl(trackDto.getMainMapMobileUrl());
        }
        if (trackDto.getMainMapWebUrl() != null) {
            existingTrack.setMainMapWebUrl(trackDto.getMainMapWebUrl());
        }

        TrackEntity updatedTrack = trackRepository.save(existingTrack);
        return trackMapper.toDto(updatedTrack);
    }

    public void deleteTrack(Long id) {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException("Трасса с ID " + id + " не найдена");
        }
        trackRepository.deleteById(id);
    }

    public TrackDto copyTrack(Long id) {
        TrackEntity originalTrack = trackRepository.findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + id + " не найдена"));

        // Создаем новую трассу с теми же данными, но без ID
        TrackEntity copiedTrack = new TrackEntity();
        copiedTrack.setState(originalTrack.getState());
        copiedTrack.setAddress(originalTrack.getAddress());
        copiedTrack.setThumbnailUrl(originalTrack.getThumbnailUrl());
        copiedTrack.setInstructionUrl(originalTrack.getInstructionUrl());
        copiedTrack.setNotes(originalTrack.getNotes());
        copiedTrack.setMainMapMobileUrl(originalTrack.getMainMapMobileUrl());
        copiedTrack.setMainMapWebUrl(originalTrack.getMainMapWebUrl());

        TrackEntity savedTrack = trackRepository.save(copiedTrack);
        return trackMapper.toDto(savedTrack);
    }
}

