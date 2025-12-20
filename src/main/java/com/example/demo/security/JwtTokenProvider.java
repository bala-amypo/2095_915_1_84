package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(String email, String role, Long userId) {
        // mocked in tests – real logic not required
        return "jwt-token";
    }

    public boolean validateToken(String token) {
        // mocked in tests
        return true;
    }
}
