package com.example.demo.service;

public interface AuthService {

    /**
     * Authenticate user and return JWT token
     */
    String login(String email, String password);

    /**
     * Validate a JWT token
     */
    boolean validateToken(String token);

    /**
     * Extract user email from token
     */
    String getEmailFromToken(String token);

    /**
     * Extract role from token
     */
    String getRoleFromToken(String token);

    /**
     * Extract userId from token
     */
    Long getUserIdFromToken(String token);
}
