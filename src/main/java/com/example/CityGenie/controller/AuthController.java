package com.example.CityGenie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CityGenie.config.JwtUtil;
import com.example.CityGenie.dto.LoginRequest;
import com.example.CityGenie.dto.LoginResponse;
import com.example.CityGenie.dto.SignUpRequest;
import com.example.CityGenie.dto.UserResponse;
import com.example.CityGenie.entity.RefreshToken;
import com.example.CityGenie.entity.User;
import com.example.CityGenie.repository.UserRepository;
import com.example.CityGenie.service.AuthService;
import com.example.CityGenie.service.RefreshTokenService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    public AuthController(RefreshTokenService rts, JwtUtil jwtUtil, UserRepository userRepo) {
        this.refreshTokenService = rts;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignUpRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // LoginResponse response = authService.login(request);
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody String refreshToken) {
        RefreshToken token = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        User user = token.getUser();
        String newAccess = jwtUtil.generateToken(user.getEmail(), user.getRole());
        UserResponse userResp = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        long expiresAt = System.currentTimeMillis() + jwtUtil.getExpirationTime();

        return ResponseEntity.ok(new LoginResponse(newAccess, refreshToken, userResp, expiresAt));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.ok().build();
    }

}
