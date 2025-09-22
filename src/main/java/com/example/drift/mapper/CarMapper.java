package com.example.drift.mapper;

import com.example.drift.dto.CarDto;
import com.example.drift.dto.CreateCarDto;
import com.example.drift.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    
    @Mapping(target = "userId", source = "user.id")
    CarDto toDto(CarEntity entity);
    
    @Mapping(target = "user", ignore = true)
    CarEntity toEntity(CarDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CarEntity toEntity(CreateCarDto dto);
    
    default List<CarDto> toDtoList(List<CarEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
