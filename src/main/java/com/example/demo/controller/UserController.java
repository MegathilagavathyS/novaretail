package com.example.demo.controller;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ALL USERS
    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Integer id) {

        return userService.getUserById(id);
    }

    // GET USER BY Email
    @GetMapping("/email/{email}")
    public UserResponseDTO getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    // GET USER BY Name
    @GetMapping("/name/{name}")
    public List<UserResponseDTO> getUsersByName(
            @PathVariable String name) {

        return userService.getUsersByName(name);
    }

    @GetMapping("/paged")
    public Page<UserResponseDTO> getUsersWithPagination(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy) {

        return userService.getUsersWithPagination(
                page,
                size,
                sortBy
        );
    }

    // CREATE USER
    @PostMapping
    public UserResponseDTO addUser(
            @Valid @RequestBody UserRequestDTO dto) {

        return userService.addUser(dto);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequestDTO dto) {

        return userService.updateUser(id, dto);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Integer id) {

        return userService.deleteUser(id);
    }
}