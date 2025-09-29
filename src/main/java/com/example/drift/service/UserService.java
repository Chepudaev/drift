package com.example.drift.service;

import com.example.drift.dto.CreateUserDto;
import com.example.drift.dto.UserDto;
import com.example.drift.entity.Role;
import com.example.drift.entity.UserEntity;
import com.example.drift.exception.UserAlreadyExistsException;
import com.example.drift.exception.UserNotFoundException;
import com.example.drift.mapper.UserMapper;
import com.example.drift.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        
        // Проверяем права доступа
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        UserEntity currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Текущий пользователь не найден"));
        
        // USER и JUDGE могут изменять только свои данные, ADMIN и MANAGER - любые
        boolean canModify = currentUser.getRoles().contains(Role.ROLE_ADMIN) || 
                           currentUser.getRoles().contains(Role.ROLE_MANAGER) ||
                           existingUser.getId().equals(currentUser.getId());
        
        if (!canModify) {
            throw new RuntimeException("Недостаточно прав для изменения данных пользователя");
        }
        
        // Проверяем, не используется ли email другим пользователем
        if (!existingUser.getEmail().equals(updateUserDto.getEmail()) && 
            userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с email " + updateUserDto.getEmail() + " уже существует");
        }
        
        // Обновляем поля
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