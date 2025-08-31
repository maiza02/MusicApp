package com.music;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.music.entity.Role;
import com.music.entity.User;
import com.music.repository.UserRepository;
import com.music.security.CustomSuccessHandler;

import jakarta.annotation.PostConstruct;

/**
 * Security configuration for the Music application.
 * 
 * This class configures Spring Security features including:
 * - Default admin user creation
 * - User authentication
 * - URL access rules
 * - Custom login success handling
 */
@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    /**
     * Initializes a default admin user if one does not already exist in the database.
     * 
     * This runs after the bean is created (@PostConstruct).
     */
    @PostConstruct
    public void initDefaultAdmin() {
        logger.info("Checking if default admin user exists...");

        User adminUser = userRepository.findByUsername("admin").orElse(null);

        if (adminUser == null) {
            logger.warn("Default admin user not found. Creating one now...");
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));

            Role adminRole = new Role("ADMIN");
            adminRole.setUser(user);

            user.setRoles(List.of(adminRole));
            userRepository.save(user);

            logger.info("Default admin user created with username='admin'.");
        } else {
            logger.info("Default admin user already exists. Skipping creation.");
        }
    }

    /**
     * Loads user details for authentication.
     * 
     * @return UserDetailsService implementation that retrieves users from the database
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            logger.debug("Attempting to load user: {}", username);
            return userRepository.findByUsername(username)
                    .map(user -> {
                        logger.info("User '{}' found. Assigning roles: {}", 
                                    user.getUsername(), 
                                    user.getRoles().stream().map(Role::getRole).toList());
                        return org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .roles(user.getRoles().stream()
                                        .map(Role::getRole) // DB values: "ADMIN" or "USER"
                                        .toArray(String[]::new))
                                .build();
                    })
                    .orElseThrow(() -> {
                        logger.error("User '{}' not found in database.", username);
                        return new UsernameNotFoundException("User not found");
                    });
        };
    }

    /**
     * Defines the security filter chain, including:
     * - Public and restricted URL mappings
     * - Custom login page and success handler
     * - Logout handling
     * 
     * @param http Spring Security HttpSecurity
     * @return configured SecurityFilterChain
     * @throws Exception if a configuration error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain...");

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/albums/**", "/searching/**", "/login", "/register", "/css/**", "/user/add").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        logger.info("Security filter chain configuration completed.");

        return http.build();
    }
}
