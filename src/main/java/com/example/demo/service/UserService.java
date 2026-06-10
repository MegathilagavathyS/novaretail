package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.Profile;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    public UserService(
            UserRepository userRepository,
            AddressRepository addressRepository,
            ProfileRepository profileRepository,
            RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
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
                        new UserNotFoundException("User not found"));

        return mapToResponseDTO(user);
    }

    // CREATE USER
    public UserResponseDTO addUser(UserRequestDTO dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if (dto.getAddresses() != null) {

            List<Address> addresses =
                    dto.getAddresses()
                            .stream()
                            .map(addressDTO -> {

                                Address address = new Address();

                                address.setCity(addressDTO.getCity());
                                address.setState(addressDTO.getState());
                                address.setPincode(addressDTO.getPincode());

                                address.setUser(user);

                                return address;

                            }).toList();

            user.setAddresses(addresses);
        }

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    // UPDATE USER
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

    // DELETE USER
    public String deleteUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        userRepository.delete(user);

        return "User deleted successfully";
    }

    // FIND USER BY EMAIL
    public UserResponseDTO getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email));

        return mapToResponseDTO(user);
    }

    // FIND USERS BY NAME
    public List<UserResponseDTO> getUsersByName(String name) {

        List<User> users = userRepository.findByName(name);

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
                        new UserNotFoundException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    // GET ALL ADDRESSES OF A USER
    public List<Address> getUserAddresses(
            Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return user.getAddresses();
    }

    // ADD PROFILE TO USER
    public Profile addProfile(
            Integer userId,
            Profile profile) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        profile.setUser(user);

        return profileRepository.save(profile);
    }

    // GET PROFILE OF USER
//    public Profile getUserProfile(
//            Integer userId) {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() ->
//                        new UserNotFoundException("User not found"));
//
//        return user.getProfile();
//    }
    public Profile getProfileByUserId(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        return user.getProfile();
    }

    public Role createRole(String roleName) {

        Role role = new Role();

        role.setRoleName(roleName);

        return roleRepository.save(role);
    }

    public User assignRoleToUser(
            Integer userId,
            Integer roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Role not found"));

        user.getRoles().add(role);

        return userRepository.save(user);
    }
}