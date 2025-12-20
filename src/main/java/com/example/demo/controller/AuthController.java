package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        return authService.login(email, password);
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam String token) {
        return authService.validateToken(token);
    }
}
