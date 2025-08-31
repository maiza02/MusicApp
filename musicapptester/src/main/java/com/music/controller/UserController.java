package com.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.music.entity.Role;
import com.music.entity.User;
import com.music.repository.UserRepository;

import java.util.List;

/**
 * UserController
 * 
 * Handles operations related to users, such as creating users
 * and listing existing users.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Displays the form to create a new user.
     *
     * @param model the model to pass a new User object to the view
     * @return the user form view
     */
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        logger.info("Accessed Add User form");
        model.addAttribute("user", new User());
        return "userform";
    }

    /**
     * Handles the submission of the Add User form.
     * Creates a new user, encodes their password, assigns default role, and saves them.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email    the email of the new user
     * @return redirect to login page after successful creation
     */
    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email) {
        logger.info("Creating new user with username: {}", username);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setEnabled(true);

        // Assign role "ROLE_USER" by default
        Role userRole = new Role("ROLE_USER");
        userRole.setUser(user);
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        logger.info("User '{}' created successfully.", username);

        return "redirect:/login";
    }

    /**
     * Lists all users in the system.
     *
     * @param model the model to pass user list to the view
     * @return the user list view
     */
    @GetMapping("/list")
    public String listUsers(Model model) {
        logger.info("Fetching all users");
        model.addAttribute("users", userRepository.findAll());
        return "userlist";
    }
}
