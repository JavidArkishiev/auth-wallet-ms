package com.example.authwalletms.service;

import com.example.authwalletms.model.User;

import java.util.List;

public interface JwtService {
    String generateToken(User user);

    String generateRefreshToken(User user);

    String extractUsername(String token);

    String extractUserId(String token);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);

    String extractPhoneNumber(String token);

    List<String> getRoles(String token);
}
