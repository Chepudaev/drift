package com.example.drift.service;

import com.example.drift.dto.CreateEventDto;
import com.example.drift.dto.EventDto;
import com.example.drift.entity.EventEntity;
import com.example.drift.entity.ScheduleEntity;
import com.example.drift.entity.TrackEntity;
import com.example.drift.exception.EventNotFoundException;
import com.example.drift.exception.ScheduleNotFoundException;
import com.example.drift.exception.TrackNotFoundException;
import com.example.drift.mapper.EventMapper;
import com.example.drift.repository.EventRepository;
import com.example.drift.repository.ScheduleRepository;
import com.example.drift.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final ScheduleRepository scheduleRepository;
    private final EventMapper eventMapper;

    @Transactional(readOnly = true)
    public List<EventDto> getAllEvents() {
        List<EventEntity> events = eventRepository.findAll();
        return eventMapper.toDtoList(events);
    }

    @Transactional(readOnly = true)
    public EventDto getEventById(Long id) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не найдено"));
        return eventMapper.toDto(event);
    }

    public EventDto createEvent(CreateEventDto createEventDto) {
        ScheduleEntity schedule;
        if (createEventDto.getScheduleId() == null) {
            schedule = null;
        } else {
            schedule = scheduleRepository.findById(createEventDto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + createEventDto.getScheduleId() + " не найдено"));
        }
        // Проверяем существование расписания
//        ScheduleEntity schedule = scheduleRepository.findById(createEventDto.getScheduleId())
//                .orElse(null);
//                .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + createEventDto.getScheduleId() + " не найдено"));

        EventEntity event = eventMapper.toEntity(createEventDto);
        event.setSchedule(schedule);

        EventEntity savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    public EventDto updateEvent(Long id, EventDto eventDto) {
        EventEntity existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Событие с ID " + id + " не найдено"));

        // Обновляем только переданные поля
        if (eventDto.getDate() != null) {
            existingEvent.setDate(eventDto.getDate());
        }
        if (eventDto.getDriverLimit() != null) {
            existingEvent.setDriverLimit(eventDto.getDriverLimit());
        }
        if (eventDto.getSpectatorLimit() != null) {
            existingEvent.setSpectatorLimit(eventDto.getSpectatorLimit());
        }
        if (eventDto.getEventType() != null) {
            existingEvent.setEventType(eventDto.getEventType());
        }
        if (eventDto.getSpectatorPrice() != null) {
            existingEvent.setSpectatorPrice(eventDto.getSpectatorPrice());
        }
        if (eventDto.getDriverPrice() != null) {
            existingEvent.setDriverPrice(eventDto.getDriverPrice());
        }
        if (eventDto.getMobileEventBannerUrl() != null) {
            existingEvent.setMobileEventBannerUrl(eventDto.getMobileEventBannerUrl());
        }
        if (eventDto.getBrowserEventBannerUrl() != null) {
            existingEvent.setBrowserEventBannerUrl(eventDto.getBrowserEventBannerUrl());
        }
        if (eventDto.getEventListThumbnailUrl() != null) {
            existingEvent.setEventListThumbnailUrl(eventDto.getEventListThumbnailUrl());
        }

        EventEntity updatedEvent = eventRepository.save(existingEvent);
        return eventMapper.toDto(updatedEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Событие с ID " + id + " не найдено");
        }
        eventRepository.deleteById(id);
    }
}




