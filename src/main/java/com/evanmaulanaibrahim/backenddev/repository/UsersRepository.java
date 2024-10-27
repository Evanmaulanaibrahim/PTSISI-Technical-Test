package com.evanmaulanaibrahim.backenddev.repository;

import com.evanmaulanaibrahim.backenddev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
