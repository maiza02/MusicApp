package com.music.controller;

import java.util.Optional;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        User user = optionalUser.get(); 
        if (passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/albums";
        } else {
            model.addAttribute("error", "Invalid password");
            return "login";
        }
    }

}
