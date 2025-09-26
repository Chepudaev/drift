package com.example.drift.mapper;

import com.example.drift.dto.CreateScheduleDto;
import com.example.drift.dto.ScheduleDto;
import com.example.drift.dto.UpdateScheduleDto;
import com.example.drift.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = ScheduleElementMapper.class)
public interface ScheduleMapper {
    
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    
    ScheduleDto toDto(ScheduleEntity entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleElements", ignore = true)
    ScheduleEntity toEntityFromCreateDto(CreateScheduleDto dto);
    
    @Mapping(target = "scheduleElements", ignore = true)
    ScheduleEntity toEntityFromDto(ScheduleDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleElements", ignore = true)
    ScheduleEntity toEntityFromUpdateDto(UpdateScheduleDto dto);
    
    default List<ScheduleDto> toDtoList(List<ScheduleEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
