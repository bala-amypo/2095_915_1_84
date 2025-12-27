package com.example.demo.service;

import com.example.demo.dto.AuthRequest;

public interface AuthService {

    void register(AuthRequest request);

    String login(AuthRequest request);
}
