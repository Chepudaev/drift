package com.example.drift.service;

import com.example.drift.dto.CreateScheduleElementDto;
import com.example.drift.dto.ScheduleElementDto;
import com.example.drift.dto.UpdateScheduleElementDto;
import com.example.drift.entity.ScheduleElementEntity;
import com.example.drift.entity.ScheduleEntity;
import com.example.drift.exception.ScheduleElementNotFoundException;
import com.example.drift.exception.ScheduleNotFoundException;
import com.example.drift.mapper.ScheduleElementMapper;
import com.example.drift.repository.ScheduleElementRepository;
import com.example.drift.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleElementService {
    
    private final ScheduleElementRepository scheduleElementRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleElementMapper scheduleElementMapper;
    
    @Transactional(readOnly = true)
    public List<ScheduleElementDto> getAllScheduleElements() {
        List<ScheduleElementEntity> elements = scheduleElementRepository.findAll();
        return scheduleElementMapper.toDtoList(elements);
    }
    
    @Transactional(readOnly = true)
    public ScheduleElementDto getScheduleElementById(Long id) {
        ScheduleElementEntity element = scheduleElementRepository.findById(id)
                .orElseThrow(() -> new ScheduleElementNotFoundException("Элемент расписания с ID " + id + " не найден"));
        return scheduleElementMapper.toDto(element);
    }
    
    @Transactional(readOnly = true)
    public List<ScheduleElementDto> getScheduleElementsByScheduleId(Long scheduleId) {
        List<ScheduleElementEntity> elements = scheduleElementRepository.findByScheduleIdOrderByStartTime(scheduleId);
        return scheduleElementMapper.toDtoList(elements);
    }
    
    public ScheduleElementDto createScheduleElement(CreateScheduleElementDto createScheduleElementDto) {
        // Проверяем, что расписание существует
        ScheduleEntity schedule = scheduleRepository.findById(createScheduleElementDto.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + createScheduleElementDto.getScheduleId() + " не найдено"));
        
        ScheduleElementEntity element = scheduleElementMapper.toEntityFromCreateDto(createScheduleElementDto);
        element.setSchedule(schedule);
        
        ScheduleElementEntity savedElement = scheduleElementRepository.save(element);
        return scheduleElementMapper.toDto(savedElement);
    }
    
    public ScheduleElementDto updateScheduleElement(Long id, UpdateScheduleElementDto updateScheduleElementDto) {
        ScheduleElementEntity existingElement = scheduleElementRepository.findById(id)
                .orElseThrow(() -> new ScheduleElementNotFoundException("Элемент расписания с ID " + id + " не найден"));
        
        // Проверяем, что расписание существует, если указан новый ID
        if (updateScheduleElementDto.getScheduleId() != null && 
            !updateScheduleElementDto.getScheduleId().equals(existingElement.getSchedule().getId())) {
            ScheduleEntity schedule = scheduleRepository.findById(updateScheduleElementDto.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException("Расписание с ID " + updateScheduleElementDto.getScheduleId() + " не найдено"));
            existingElement.setSchedule(schedule);
        }
        
        // Обновляем поля только если они указаны
        if (updateScheduleElementDto.getStartTime() != null) {
            existingElement.setStartTime(updateScheduleElementDto.getStartTime());
        }
        if (updateScheduleElementDto.getEndTime() != null) {
            existingElement.setEndTime(updateScheduleElementDto.getEndTime());
        }
        if (updateScheduleElementDto.getDescription() != null) {
            existingElement.setDescription(updateScheduleElementDto.getDescription());
        }
        
        ScheduleElementEntity updatedElement = scheduleElementRepository.save(existingElement);
        return scheduleElementMapper.toDto(updatedElement);
    }
    
    public void deleteScheduleElement(Long id) {
        if (!scheduleElementRepository.existsById(id)) {
            throw new ScheduleElementNotFoundException("Элемент расписания с ID " + id + " не найден");
        }
        scheduleElementRepository.deleteById(id);
    }
}
