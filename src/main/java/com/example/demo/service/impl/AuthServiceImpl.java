package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AppUserRepository appUserRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.appUserRepository = appUserRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(String email, String password) {

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Basic password check (NO encoding, as requested)
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Invalid credentials");
        }

        return jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole(),
                user.getId()
        );
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String getEmailFromToken(String token) {
        // Basic stub – extend later if needed
        return null;
    }

    @Override
    public String getRoleFromToken(String token) {
        // Basic stub – extend later if needed
        return null;
    }

    @Override
    public Long getUserIdFromToken(String token) {
        // Basic stub – extend later if needed
        return null;
    }
}
