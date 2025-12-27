package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        // Simple hardcoded authentication for demo
        if ("admin@example.com".equals(request.getEmail()) && "password".equals(request.getPassword())) {
            String token = jwtTokenProvider.generateToken(request.getEmail(), "ADMIN", 1L);
            return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), "ADMIN", 1L));
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        // Simple registration for demo
        String token = jwtTokenProvider.generateToken(request.getEmail(), "USER", 2L);
        return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), "USER", 2L));
    }
}