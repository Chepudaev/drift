package com.example.drift.mapper;

import com.example.drift.dto.*;
import com.example.drift.entity.EventEntity;
import com.example.drift.entity.FaceToFaceEntity;
import com.example.drift.entity.ScheduleEntity;
import com.example.drift.entity.TrackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TrackMapper.class, ScheduleMapper.class, FaceToFaceMapper.class})
public interface EventMapper {
    
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    
    @Mapping(target = "track", source = "track", qualifiedByName = "trackToDto")
    @Mapping(target = "faceToFaceEntities", source = "faceToFaceEntities", qualifiedByName = "faceToFaceToDto")
    @Mapping(target = "schedule", source = "schedule", qualifiedByName = "scheduleToDto")
    EventDto toDto(EventEntity entity);
    
    @Mapping(target = "track", ignore = true)
    @Mapping(target = "faceToFaceEntities", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    EventEntity toEntity(EventDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "track", ignore = true)
    @Mapping(target = "faceToFaceEntities", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    EventEntity toEntity(CreateEventDto dto);
    
    default List<EventDto> toDtoList(List<EventEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
