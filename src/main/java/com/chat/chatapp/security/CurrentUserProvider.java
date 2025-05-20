package com.chat.chatapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        return authentication.getName();
    }

    public Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Jwt)) {
            throw new RuntimeException("Principal is not a JWT");
        }

        return (Jwt) principal;
    }

    public String getClaim(String claimName) {
        Jwt jwt = getJwt();
        return jwt.getClaimAsString(claimName);
    }
}
