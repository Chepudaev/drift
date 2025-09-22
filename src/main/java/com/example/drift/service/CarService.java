package com.example.drift.service;

import com.example.drift.dto.CarDto;
import com.example.drift.dto.CreateCarDto;
import com.example.drift.entity.CarEntity;
import com.example.drift.entity.UserEntity;
import com.example.drift.exception.CarNotFoundException;
import com.example.drift.exception.UserNotFoundException;
import com.example.drift.mapper.CarMapper;
import com.example.drift.repository.CarRepository;
import com.example.drift.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarMapper carMapper;
    
    @Transactional(readOnly = true)
    public List<CarDto> getAllCars() {
        List<CarEntity> cars = carRepository.findAllWithUser();
        return carMapper.toDtoList(cars);
    }
    
    @Transactional(readOnly = true)
    public CarDto getCarById(Long id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Машина с ID " + id + " не найдена"));
        return carMapper.toDto(car);
    }
    
    @Transactional(readOnly = true)
    public List<CarDto> getCarsByUserId(Long userId) {
        List<CarEntity> cars = carRepository.findByUserIdWithUser(userId);
        return carMapper.toDtoList(cars);
    }
    
    public CarDto createCar(CreateCarDto createCarDto) {
        UserEntity user = userRepository.findById(createCarDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + createCarDto.getUserId() + " не найден"));
        
        CarEntity car = carMapper.toEntity(createCarDto);
        car.setUser(user);
        
        CarEntity savedCar = carRepository.save(car);
        return carMapper.toDto(savedCar);
    }
    
    public CarDto updateCar(Long id, CreateCarDto updateCarDto) {
        CarEntity existingCar = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Машина с ID " + id + " не найдена"));
        
        UserEntity user = userRepository.findById(updateCarDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + updateCarDto.getUserId() + " не найден"));
        
        // Обновляем поля
        existingCar.setBrand(updateCarDto.getBrand());
        existingCar.setModel(updateCarDto.getModel());
        existingCar.setHorsepower(updateCarDto.getHorsepower());
        existingCar.setUserPhotoUrl(updateCarDto.getUserPhotoUrl());
        existingCar.setModeratorPhotoUrl(updateCarDto.getModeratorPhotoUrl());
        existingCar.setYear(updateCarDto.getYear());
        existingCar.setColor(updateCarDto.getColor());
        existingCar.setCarClass(updateCarDto.getCarClass());
        existingCar.setColor1(updateCarDto.getColor1());
        existingCar.setColor2(updateCarDto.getColor2());
        existingCar.setColor3(updateCarDto.getColor3());
        existingCar.setUser(user);
        
        CarEntity updatedCar = carRepository.save(existingCar);
        return carMapper.toDto(updatedCar);
    }
    
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException("Машина с ID " + id + " не найдена");
        }
        carRepository.deleteById(id);
    }
}
