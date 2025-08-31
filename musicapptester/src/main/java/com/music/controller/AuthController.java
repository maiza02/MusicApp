package com.music.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.entity.User;
import com.music.repository.UserRepository;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Displays the login page.
     *
     * @return the login view name
     */
    @GetMapping("/login")
    public String showLoginForm() {
        logger.debug("Accessed login page");
        return "login";
    }

    /**
     * Handles login form submissions.
     * Validates username and password, then redirects or returns errors.
     *
     * @param username the entered username
     * @param password the entered password
     * @param model    model to pass error messages to the view
     * @return redirect to albums if successful, otherwise return to login page with error
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        logger.info("Login attempt for username: {}", username);

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            logger.warn("Login failed - user not found: {}", username);
            model.addAttribute("error", "User not found");
            return "login";
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            logger.info("Login successful for username: {}", username);
            return "redirect:/albums";
        } else {
            logger.warn("Login failed - invalid password for username: {}", username);
            model.addAttribute("error", "Invalid password");
            return "login";
        }
    }
}
