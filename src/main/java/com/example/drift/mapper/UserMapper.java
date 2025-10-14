package com.example.drift.mapper;

import com.example.drift.dto.CreateUserDto;
import com.example.drift.dto.UserDto;
import com.example.drift.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserDto toDto(UserEntity entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "roles", ignore = true) // Роли устанавливаются отдельно
    UserEntity toEntityFromCreateDto(CreateUserDto dto);
    
    default List<UserDto> toDtoList(List<UserEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
