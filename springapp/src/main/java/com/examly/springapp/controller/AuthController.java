package com.examly.springapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.AuthRequest;
import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.security.AdminUserDetailsService;
import com.examly.springapp.security.JwtUtil;
import com.examly.springapp.security.LoginAttemptService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        String username = request.getUsername();

        // FR12.1: Check if account is locked due to brute-force
        if (loginAttemptService.isBlocked(username)) {
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body(Map.of(
                            "status", 423,
                            "error", "Account Locked",
                            "message", "Account is temporarily locked due to too many failed login attempts. Please try again later."
                    ));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            request.getPassword()));

            // Login succeeded — reset attempts
            loginAttemptService.loginSucceeded(username);

            UserDetails userDetails = adminUserDetailsService.loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException ex) {
            // Login failed — increment attempts
            loginAttemptService.loginFailed(username);

            int remaining = loginAttemptService.getRemainingAttempts(username);
            String message = remaining > 0
                    ? "Invalid username or password. " + remaining + " attempt(s) remaining."
                    : "Account is now locked due to too many failed login attempts.";

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "status", 401,
                            "error", "Unauthorized",
                            "message", message
                    ));
        }
    }
}