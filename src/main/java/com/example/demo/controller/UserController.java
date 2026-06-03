package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    List<User> users = new ArrayList<>();

    // GET API
    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    // POST API
    @PostMapping
    public String addUser(@RequestBody User user) {
        users.add(user);
        return "User added successfully";
    }

    // PUT API
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id,
                             @RequestBody User updatedUser) {

        for (User user : users) {
            if (user.getId() == id) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return "User updated";
            }
        }

        return "User not found";
    }

    // DELETE API
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {

        users.removeIf(user -> user.getId() == id);

        return "User deleted";
    }
}