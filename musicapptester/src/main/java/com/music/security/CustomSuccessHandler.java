package com.music.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Custom authentication success handler.
 * Redirects users to different pages based on their roles after successful login.
 */
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        logger.info("User '{}' logged in with roles: {}", authentication.getName(), roles);

        if (roles.contains("ROLE_ADMIN")) {
            logger.info("Redirecting admin user '{}' to /admin", authentication.getName());
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            logger.info("Redirecting standard user '{}' to /albums", authentication.getName());
            response.sendRedirect("/albums");
        } else {
            logger.warn("User '{}' has no recognized roles; redirecting to home", authentication.getName());
            response.sendRedirect("/");
        }
    }
}
