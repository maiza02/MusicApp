package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
