package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.music.entity.Role;

/**
 * Repository interface for Role entity.
 * Provides CRUD operations for roles.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    
}
