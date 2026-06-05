package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convert Entity -> Response DTO
    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Get All Users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Get User By Id
    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return mapToResponseDTO(user);
    }

    // Add User
    public UserResponseDTO addUser(UserRequestDTO dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    // Update User
    public UserResponseDTO updateUser(
            Integer id,
            UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);

        return mapToResponseDTO(updatedUser);
    }

    // Delete User
    public String deleteUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        userRepository.delete(user);

        return "User deleted successfully";
    }
}