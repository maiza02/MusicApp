package com.music.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.User;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom queries for users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the matching User if found, or empty if not
     */
    Optional<User> findByUsername(String username);
}
