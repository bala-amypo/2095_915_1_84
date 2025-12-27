package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.AppUser;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserService appUserService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AppUser user = appUserService.findByEmail(request.getEmail());
        
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole().name(), user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser = appUserService.createUser(user);
        
        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getRole().name(), savedUser.getId());
        
        return ResponseEntity.ok(new AuthResponse(token, savedUser.getEmail(), savedUser.getRole().name(), savedUser.getId()));
    }
}