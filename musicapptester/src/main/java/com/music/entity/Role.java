package com.music.entity;

import jakarta.persistence.*;

/**
 * Role entity representing the "roles" table in the database.
 * Each role is associated with a single user. Used for user authorization.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private String role; // Name of the role, e.g., "ROLE_USER" or "ROLE_ADMIN"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The user this role belongs to

    // --- Constructors ---

    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
