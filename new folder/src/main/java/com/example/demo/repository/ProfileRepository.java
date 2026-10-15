package com.example.demo.repository;

import com.example.demo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository
        extends JpaRepository<Profile, Integer> {
}