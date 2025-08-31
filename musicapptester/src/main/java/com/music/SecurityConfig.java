package com.music;

import java.util.List;
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

@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }


    @PostConstruct
    public void initDefaultAdmin() {
        User adminUser = userRepository.findByUsername("admin").orElse(null);
        if (adminUser == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));

            Role adminRole = new Role("ADMIN");
            adminRole.setUser(user);

            user.setRoles(List.of(adminRole));
            userRepository.save(user);
        }
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream()
                                .map(Role::getRole) // DB values: "ADMIN" or "USER"
                                .toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/albums/**", "/searching/**", "/login", "/register", "/css/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN") 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler) 
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
