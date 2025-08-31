package com.music;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Application configuration class.
 * 
 * This class defines Spring beans that are used across the application.
 * It is annotated with @Configuration, which tells Spring that this 
 * class provides bean definitions to the application context.
 */
@Configuration
public class AppConfig {

    /**
     * Provides a BCryptPasswordEncoder bean.
     *
     * BCryptPasswordEncoder is used to hash and verify user passwords 
     * in a secure manner. Spring Security will use this encoder for 
     * encrypting and matching passwords.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
