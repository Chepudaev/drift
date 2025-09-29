package com.example.drift.mapper;

import com.example.drift.dto.*;
import com.example.drift.entity.FaceToFaceEntity;
import com.example.drift.entity.RoundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FaceToFaceMapper {
    
    FaceToFaceMapper INSTANCE = Mappers.getMapper(FaceToFaceMapper.class);
    
    @Mapping(target = "rounds", ignore = true)
    @Mapping(target = "user1Id", source = "user1.id")
    @Mapping(target = "user2Id", source = "user2.id")
    @Mapping(target = "autoUser1Id", source = "autoUser1.id")
    @Mapping(target = "autoUser2Id", source = "autoUser2.id")
    @Mapping(target = "eventId", source = "event.id")
    @Named("faceToFaceToDto")
    FaceToFaceDto toDto(FaceToFaceEntity entity);
    
    @Mapping(target = "rounds", source = "rounds")
    @Mapping(target = "user1Id", source = "user1.id")
    @Mapping(target = "user2Id", source = "user2.id")
    @Mapping(target = "autoUser1Id", source = "autoUser1.id")
    @Mapping(target = "autoUser2Id", source = "autoUser2.id")
    @Mapping(target = "eventId", source = "event.id")
    FaceToFaceDto toDtoWithRounds(FaceToFaceEntity entity);
    
    @Mapping(target = "rounds", ignore = true)
    @Mapping(target = "user1", ignore = true)
    @Mapping(target = "user2", ignore = true)
    @Mapping(target = "autoUser1", ignore = true)
    @Mapping(target = "autoUser2", ignore = true)
    FaceToFaceEntity toEntity(FaceToFaceDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rounds", ignore = true)
    @Mapping(target = "user1", ignore = true)
    @Mapping(target = "user2", ignore = true)
    @Mapping(target = "autoUser1", ignore = true)
    @Mapping(target = "autoUser2", ignore = true)
    FaceToFaceEntity toEntity(CreateFaceToFaceDto dto);
    
    @Mapping(target = "faceToFaceId", source = "faceToFace.id")
    RoundDto toRoundDto(RoundEntity entity);
    
    @Mapping(target = "faceToFace", ignore = true)
    RoundEntity toRoundEntity(RoundDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "faceToFace", ignore = true)
    RoundEntity toRoundEntity(CreateRoundDto dto);
    
    default List<FaceToFaceDto> toDtoList(List<FaceToFaceEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
    
    default List<RoundDto> toRoundDtoList(List<RoundEntity> entities) {
        return entities.stream()
                .map(this::toRoundDto)
                .toList();
    }
}
