package com.example.drift.mapper;

import com.example.drift.dto.CarDto;
import com.example.drift.dto.CreateCarDto;
import com.example.drift.entity.CarEntity;
import com.example.drift.entity.TyreClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarMapper {
    
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tyreClasses", expression = "java(mapTyreClasses(entity.getTyreClasses()))")
    CarDto toDto(CarEntity entity);
    
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tyreClasses", ignore = true)
    CarEntity toEntity(CarDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tyreClasses", ignore = true)
    CarEntity toEntity(CreateCarDto dto);
    
    default List<CarDto> toDtoList(List<CarEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
    
    default Set<String> mapTyreClasses(Set<TyreClass> tyreClasses) {
        if (tyreClasses == null) {
            return null;
        }
        return tyreClasses.stream()
                .map(TyreClass::getTyreClass)
                .collect(Collectors.toSet());
    }
}
