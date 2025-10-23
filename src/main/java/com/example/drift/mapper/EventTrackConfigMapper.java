package com.example.drift.mapper;

import com.example.drift.dto.CreateEventTrackConfigDto;
import com.example.drift.dto.EventTrackConfigDto;
import com.example.drift.entity.EventTrackConfigEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class, TrackMapper.class})
public interface EventTrackConfigMapper {

    EventTrackConfigMapper INSTANCE = Mappers.getMapper(EventTrackConfigMapper.class);

    @Mapping(target = "event", source = "event")
    @Mapping(target = "track", source = "track")
    @Mapping(target = "trackConfig", source = "trackConfig")
    EventTrackConfigDto toDto(EventTrackConfigEntity entity);

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "track", ignore = true)
    @Mapping(target = "trackConfig", ignore = true)
    EventTrackConfigEntity toEntity(EventTrackConfigDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "track", ignore = true)
    @Mapping(target = "trackConfig", ignore = true)
    EventTrackConfigEntity toEntity(CreateEventTrackConfigDto dto);

    default List<EventTrackConfigDto> toDtoList(List<EventTrackConfigEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}




