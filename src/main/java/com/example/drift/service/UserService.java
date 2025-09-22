package com.example.drift.service;

import com.example.drift.dto.CreateUserDto;
import com.example.drift.dto.UserDto;
import com.example.drift.entity.UserEntity;
import com.example.drift.exception.UserAlreadyExistsException;
import com.example.drift.exception.UserNotFoundException;
import com.example.drift.mapper.UserMapper;
import com.example.drift.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }
    
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        return userMapper.toDto(user);
    }
    
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с email " + createUserDto.getEmail() + " уже существует");
        }
        
        UserEntity user = userMapper.toEntityFromCreateDto(createUserDto);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    public UserDto updateUser(Long id, CreateUserDto updateUserDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        
        // Проверяем, не используется ли email другим пользователем
        if (!existingUser.getEmail().equals(updateUserDto.getEmail()) && 
            userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с email " + updateUserDto.getEmail() + " уже существует");
        }
        
        // Обновляем поля
        existingUser.setName(updateUserDto.getName());
        existingUser.setFirstName(updateUserDto.getFirstName());
        existingUser.setLastName(updateUserDto.getLastName());
        existingUser.setEmail(updateUserDto.getEmail());
        existingUser.setPhone(updateUserDto.getPhone());
        existingUser.setInstagram(updateUserDto.getInstagram());
        existingUser.setProfilePhotoUrl(updateUserDto.getProfilePhotoUrl());
        existingUser.setMotto(updateUserDto.getMotto());
        existingUser.setOfficialPhotoUrl(updateUserDto.getOfficialPhotoUrl());
        existingUser.setSponsors(updateUserDto.getSponsors());
        
        UserEntity updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
    }
}