package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    private List<AddressDTO> addresses;

    public UserRequestDTO() {
    }

    public UserRequestDTO(
            String name,
            String email,
            List<AddressDTO> addresses) {

        this.name = name;
        this.email = email;
        this.addresses = addresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(
            List<AddressDTO> addresses) {

        this.addresses = addresses;
    }
}