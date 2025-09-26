package com.example.drift.mapper;

import com.example.drift.dto.CreateScheduleElementDto;
import com.example.drift.dto.ScheduleElementDto;
import com.example.drift.dto.UpdateScheduleElementDto;
import com.example.drift.entity.ScheduleElementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleElementMapper {
    
    ScheduleElementMapper INSTANCE = Mappers.getMapper(ScheduleElementMapper.class);
    
    ScheduleElementDto toDto(ScheduleElementEntity entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    ScheduleElementEntity toEntityFromCreateDto(CreateScheduleElementDto dto);
    
    @Mapping(target = "schedule", ignore = true)
    ScheduleElementEntity toEntityFromDto(ScheduleElementDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    ScheduleElementEntity toEntityFromUpdateDto(UpdateScheduleElementDto dto);
    
    default List<ScheduleElementDto> toDtoList(List<ScheduleElementEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
    
    default List<ScheduleElementEntity> toEntityListFromCreateDto(List<CreateScheduleElementDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntityFromCreateDto)
                .toList();
    }
    
    default List<ScheduleElementEntity> toEntityListFromUpdateDto(List<UpdateScheduleElementDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntityFromUpdateDto)
                .toList();
    }
}
