package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.request.LoginRequest;
import com.payment.dto.request.RegisterRequest;
import com.payment.dto.response.LoginResponse;
import com.payment.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
 
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
    	authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
 
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
 
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}