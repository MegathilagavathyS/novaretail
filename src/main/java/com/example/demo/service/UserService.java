package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(
            UserRepository userRepository,
            AddressRepository addressRepository) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    // ENTITY -> DTO
    private UserResponseDTO mapToResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // GET USER BY ID
    public UserResponseDTO getUserById(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        return mapToResponseDTO(user);
    }

    // CREATE USER
    public UserResponseDTO addUser(
            UserRequestDTO dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User savedUser =
                userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    // UPDATE USER
    public UserResponseDTO updateUser(
            Integer id,
            UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updatedUser =
                userRepository.save(user);

        return mapToResponseDTO(updatedUser);
    }

    // DELETE USER
    public String deleteUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        userRepository.delete(user);

        return "User deleted successfully";
    }

    // FIND USER BY EMAIL
    public UserResponseDTO getUserByEmail(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email));

        return mapToResponseDTO(user);
    }

    // FIND USERS BY NAME
    public List<UserResponseDTO> getUsersByName(
            String name) {

        List<User> users =
                userRepository.findByName(name);

        if (users.isEmpty()) {
            throw new UserNotFoundException(
                    "No users found with name: " + name);
        }

        return users.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // PAGINATION + SORTING
    public Page<UserResponseDTO> getUsersWithPagination(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy));

        Page<User> usersPage =
                userRepository.findAll(pageable);

        return usersPage.map(this::mapToResponseDTO);
    }

    // ADD ADDRESS TO USER
    public Address addAddress(
            Integer userId,
            Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }
    // GET ALL ADDRESSES OF A USER
    public List<Address> getUserAddresses(
            Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        return user.getAddresses();
    }
}