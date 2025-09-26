package com.example.drift.service;

import com.example.drift.dto.CreateScheduleDto;
import com.example.drift.dto.ScheduleDto;
import com.example.drift.dto.UpdateScheduleDto;
import com.example.drift.entity.ScheduleEntity;
import com.example.drift.entity.ScheduleElementEntity;
import com.example.drift.exception.ScheduleNotFoundException;
import com.example.drift.mapper.ScheduleMapper;
import com.example.drift.mapper.ScheduleElementMapper;
import com.example.drift.repository.ScheduleRepository;
import com.example.drift.repository.ScheduleElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    private final ScheduleElementRepository scheduleElementRepository;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleElementMapper scheduleElementMapper;
    
    @Transactional(readOnly = true)
    public List<ScheduleDto> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAllWithElements();
        return scheduleMapper.toDtoList(schedules);
    }
    
    @Transactional(readOnly = true)
    public ScheduleDto getScheduleById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findByIdWithElements(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + id + " не найдено"));
        return scheduleMapper.toDto(schedule);
    }
    
    public ScheduleDto createSchedule(CreateScheduleDto createScheduleDto) {
        ScheduleEntity schedule = scheduleMapper.toEntityFromCreateDto(createScheduleDto);
        
        // Сохраняем расписание
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);
        
        // Сохраняем элементы расписания, если они есть
        if (createScheduleDto.getScheduleElements() != null && !createScheduleDto.getScheduleElements().isEmpty()) {
            List<ScheduleElementEntity> elements = scheduleElementMapper.toEntityListFromCreateDto(createScheduleDto.getScheduleElements());
            if (elements != null) {
                elements.forEach(element -> element.setSchedule(savedSchedule));
                scheduleElementRepository.saveAll(elements);
            }
        }
        
        return scheduleMapper.toDto(savedSchedule);
    }
    
    public ScheduleDto updateSchedule(Long id, UpdateScheduleDto updateScheduleDto) {
        ScheduleEntity existingSchedule = scheduleRepository.findByIdWithElements(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + id + " не найдено"));
        
        // Обновляем основные поля только если они указаны
        if (updateScheduleDto.getName() != null) {
            existingSchedule.setName(updateScheduleDto.getName());
        }
        if (updateScheduleDto.getDescription() != null) {
            existingSchedule.setDescription(updateScheduleDto.getDescription());
        }
        
        // Обновляем элементы расписания только если они явно указаны
        if (updateScheduleDto.getScheduleElements() != null) {
            // Удаляем старые элементы
            scheduleElementRepository.deleteByScheduleId(id);
            
            // Добавляем новые элементы
            if (!updateScheduleDto.getScheduleElements().isEmpty()) {
                List<ScheduleElementEntity> elements = scheduleElementMapper.toEntityListFromUpdateDto(updateScheduleDto.getScheduleElements());
                if (elements != null) {
                    elements.forEach(element -> element.setSchedule(existingSchedule));
                    scheduleElementRepository.saveAll(elements);
                }
            }
        }
        
        ScheduleEntity updatedSchedule = scheduleRepository.save(existingSchedule);
        return scheduleMapper.toDto(updatedSchedule);
    }
    
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("Расписание с ID " + id + " не найдено");
        }
        scheduleRepository.deleteById(id);
    }
}
