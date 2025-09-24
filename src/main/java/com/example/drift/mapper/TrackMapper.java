package com.example.drift.mapper;

import com.example.drift.dto.*;
import com.example.drift.entity.TrackConfigEntity;
import com.example.drift.entity.TrackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    
    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);
    
    @Mapping(target = "trackConfigs", ignore = true)
    TrackDto toDto(TrackEntity entity);
    
    @Mapping(target = "trackConfigs", source = "trackConfigs")
    TrackDto toDtoWithConfigs(TrackEntity entity);
    
    @Mapping(target = "trackConfigs", ignore = true)
    TrackEntity toEntity(TrackDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trackConfigs", ignore = true)
    TrackEntity toEntity(CreateTrackDto dto);
    
    @Mapping(target = "trackId", source = "track.id")
    TrackConfigDto toDto(TrackConfigEntity entity);
    
    @Mapping(target = "track", ignore = true)
    TrackConfigEntity toEntity(TrackConfigDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "track", ignore = true)
    TrackConfigEntity toEntity(CreateTrackConfigDto dto);
    
    default List<TrackDto> toDtoList(List<TrackEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
    
    default List<TrackConfigDto> toConfigDtoList(List<TrackConfigEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}

