package com.example.CityGenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CityGenie.config.JwtUtil;
import com.example.CityGenie.dto.LoginRequest;
import com.example.CityGenie.dto.LoginResponse;
import com.example.CityGenie.dto.SignUpRequest;
import com.example.CityGenie.dto.UserResponse;
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

    public ResponseEntity<UserResponse> signup(SignUpRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());

        userRepo.save(user);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());

        LoginResponse response = new LoginResponse(token, userResponse);
        return ResponseEntity.ok(response);
    }
}
