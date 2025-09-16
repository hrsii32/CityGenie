package com.example.CityGenie.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.CityGenie.entity.RefreshToken;
import com.example.CityGenie.entity.User;
import com.example.CityGenie.exception.TokenExpiredException;
import com.example.CityGenie.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepo;
    private final long refreshTokenDurationMs;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository repo,
            @Value("${security.jwt.refresh-expiration-ms:604800000}") long duration) {
        this.refreshTokenRepo = repo;
        this.refreshTokenDurationMs = duration; // default 7 days
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(generateSecureToken());
        return refreshTokenRepo.save(token);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(token);
            throw new TokenExpiredException("Refresh token expired. Please log in again.");
        }
        return token;
    }

    public int deleteByUser(User user) {
        return refreshTokenRepo.deleteByUser(user);
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
