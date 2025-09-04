package com.example.CityGenie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CityGenie.config.JwtUtil;
import com.example.CityGenie.entity.Role;
import com.example.CityGenie.entity.User;
import com.example.CityGenie.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Signup
    public ResponseEntity<String> signup(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists. Please login instead.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role: STUDENT if not provided
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully with role: " + user.getRole());
    }

    // Login
    public ResponseEntity<String> login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Generate JWT with role included
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(token);
    }
}
