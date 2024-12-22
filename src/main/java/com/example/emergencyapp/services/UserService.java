package com.example.emergencyapp.services;

import com.example.emergencyapp.DTO.UserRequestDto;
import com.example.emergencyapp.DTO.UserResponseDto;
import com.example.emergencyapp.models.User;
import com.example.emergencyapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setRole(userRequestDto.getRole());
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToResponseDto);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userRequestDto.getFirstName());
                    user.setLastName(userRequestDto.getLastName());
                    user.setRole(userRequestDto.getRole());
                    User updatedUser = userRepository.save(user);
                    return mapToResponseDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }

    private UserResponseDto mapToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setRole(user.getRole());
        return responseDto;
    }
}
