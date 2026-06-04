package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    // Get all users
    public List<User> getAllUsers() {
        return users;
    }

    // Add user
    public String addUser(User user) {
        users.add(user);
        return "User added successfully";
    }

    // Update user
    public String updateUser(int id, User updatedUser) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return "User updated successfully";
            }
        }
        return "User not found";
    }

    // Delete user
    public String deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
        return "User deleted successfully";
    }
}