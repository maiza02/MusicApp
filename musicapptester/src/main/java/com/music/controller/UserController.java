package com.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.music.entity.Role;
import com.music.entity.User;
import com.music.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show the "Create User" form
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userform";  
    }

    // Handle form submission for creating new user
    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // encode password
        user.setEmail(email);
        user.setEnabled(true);

        // Assign role "ROLE_USER" by default
        Role userRole = new Role("ROLE_USER");
        userRole.setUser(user);
        user.setRoles(List.of(userRole));

        userRepository.save(user);

        return "redirect:/login"; // redirect to login page
    }


    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userlist";  
    }
}
