package com.example.CityGenie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String refreshToken;
    private long expiresAt;
    private UserResponse user;

    public LoginResponse(String token, String refreshToken, UserResponse user, long expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresAt = expiresAt;

    }

}
