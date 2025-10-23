package com.example.drift.service;

import com.example.drift.dto.CreateEventTrackConfigDto;
import com.example.drift.dto.EventTrackConfigDto;
import com.example.drift.dto.UpdateEventTrackConfigDto;
import com.example.drift.entity.EventEntity;
import com.example.drift.entity.EventTrackConfigEntity;
import com.example.drift.entity.TrackConfigEntity;
import com.example.drift.entity.TrackEntity;
import com.example.drift.exception.EventNotFoundException;
import com.example.drift.exception.EventTrackConfigNotFoundException;
import com.example.drift.exception.TrackConfigNotFoundException;
import com.example.drift.exception.TrackNotFoundException;
import com.example.drift.mapper.EventTrackConfigMapper;
import com.example.drift.repository.EventRepository;
import com.example.drift.repository.EventTrackConfigRepository;
import com.example.drift.repository.TrackConfigRepository;
import com.example.drift.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventTrackConfigService {

    private final EventTrackConfigRepository eventTrackConfigRepository;
    private final EventRepository eventRepository;
    private final TrackRepository trackRepository;
    private final TrackConfigRepository trackConfigRepository;
    private final EventTrackConfigMapper eventTrackConfigMapper;

    @Transactional(readOnly = true)
    public List<EventTrackConfigDto> getAllEventTrackConfigs() {
        List<EventTrackConfigEntity> eventTrackConfigs = eventTrackConfigRepository.findAll();
        return eventTrackConfigMapper.toDtoList(eventTrackConfigs);
    }

    @Transactional(readOnly = true)
    public EventTrackConfigDto getEventTrackConfigById(Long id) {
        EventTrackConfigEntity eventTrackConfig = eventTrackConfigRepository.findById(id)
                .orElseThrow(() -> new EventTrackConfigNotFoundException("Конфигурация события с ID " + id + " не найдена"));
        return eventTrackConfigMapper.toDto(eventTrackConfig);
    }

    @Transactional(readOnly = true)
    public EventTrackConfigDto getEventTrackConfigByEventId(Long eventId) {
        EventTrackConfigEntity eventTrackConfig = eventTrackConfigRepository.findByEventId(eventId)
                .orElseThrow(() -> new EventTrackConfigNotFoundException("Конфигурация для события с ID " + eventId + " не найдена"));
        return eventTrackConfigMapper.toDto(eventTrackConfig);
    }

    public EventTrackConfigDto createEventTrackConfig(CreateEventTrackConfigDto createDto) {
        // Проверяем существование события
        EventEntity event = eventRepository.findById(createDto.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Событие с ID " + createDto.getEventId() + " не найдено"));

        // Проверяем существование трассы (если указана)
        TrackEntity track = null;
        if (createDto.getTrackId() != null) {
            track = trackRepository.findById(createDto.getTrackId())
                    .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + createDto.getTrackId() + " не найдена"));
        }

        // Проверяем существование конфигурации трассы (если указана)
        TrackConfigEntity trackConfig = null;
        if (createDto.getTrackConfigId() != null) {
            trackConfig = trackConfigRepository.findById(createDto.getTrackConfigId())
                    .orElseThrow(() -> new TrackConfigNotFoundException("Конфигурация трассы с ID " + createDto.getTrackConfigId() + " не найдена"));
        }

        EventTrackConfigEntity eventTrackConfig = eventTrackConfigMapper.toEntity(createDto);
        eventTrackConfig.setEvent(event);
        eventTrackConfig.setTrack(track);
        eventTrackConfig.setTrackConfig(trackConfig);

        EventTrackConfigEntity savedEventTrackConfig = eventTrackConfigRepository.save(eventTrackConfig);
        return eventTrackConfigMapper.toDto(savedEventTrackConfig);
    }

    public EventTrackConfigDto updateEventTrackConfig(Long id, UpdateEventTrackConfigDto updateDto) {
        EventTrackConfigEntity existingEventTrackConfig = eventTrackConfigRepository.findById(id)
                .orElseThrow(() -> new EventTrackConfigNotFoundException("Конфигурация события с ID " + id + " не найдена"));

        // Обновляем только переданные поля
        if (updateDto.getTrackId() != null) {
            TrackEntity track = trackRepository.findById(updateDto.getTrackId())
                    .orElseThrow(() -> new TrackNotFoundException("Трасса с ID " + updateDto.getTrackId() + " не найдена"));
            existingEventTrackConfig.setTrack(track);
        }

        if (updateDto.getTrackConfigId() != null) {
            TrackConfigEntity trackConfig = trackConfigRepository.findById(updateDto.getTrackConfigId())
                    .orElseThrow(() -> new TrackConfigNotFoundException("Конфигурация трассы с ID " + updateDto.getTrackConfigId() + " не найдена"));
            existingEventTrackConfig.setTrackConfig(trackConfig);
        }

        EventTrackConfigEntity updatedEventTrackConfig = eventTrackConfigRepository.save(existingEventTrackConfig);
        return eventTrackConfigMapper.toDto(updatedEventTrackConfig);
    }

    public void deleteEventTrackConfig(Long id) {
        if (!eventTrackConfigRepository.existsById(id)) {
            throw new EventTrackConfigNotFoundException("Конфигурация события с ID " + id + " не найдена");
        }
        eventTrackConfigRepository.deleteById(id);
    }
}




