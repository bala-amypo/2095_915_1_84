package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String login() {
        return "Login OK";
    }
}
