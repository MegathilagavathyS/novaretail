package com.example.demo.controller;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Profile;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

    // SEARCH BY EMAIL
    @GetMapping("/email/{email}")
    public UserResponseDTO getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    // SEARCH BY NAME
    @GetMapping("/name/{name}")
    public List<UserResponseDTO> getUsersByName(
            @PathVariable String name) {

        return userService.getUsersByName(name);
    }

    // PAGINATION + SORTING
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
                sortBy);
    }

    // ADD ADDRESS TO USER
    @PostMapping("/{userId}/addresses")
    public Address addAddress(
            @PathVariable Integer userId,
            @RequestBody Address address) {

        return userService.addAddress(
                userId,
                address);
    }

    @GetMapping("/{id}/addresses")
    public List<Address> getUserAddresses(
            @PathVariable Integer id) {

        return userService.getUserAddresses(id);
    }

    @GetMapping("/{userId}/profile")
    public Profile getProfile(
            @PathVariable Integer userId) {

        return userService.getProfileByUserId(userId);
    }

    @PostMapping("/{userId}/profile")
    public Profile addProfile(
            @PathVariable Integer userId,
            @RequestBody Profile profile) {

        return userService.addProfile(
                userId,
                profile);
    }

    @PostMapping("/roles")
    public Role createRole(
            @RequestParam String roleName) {

        return userService.createRole(roleName);
    }

    @PostMapping("/{userId}/roles/{roleId}")
    public User assignRole(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {

        return userService.assignRoleToUser(
                userId,
                roleId);
    }

}