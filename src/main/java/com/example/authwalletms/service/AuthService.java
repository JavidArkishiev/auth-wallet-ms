package com.example.authwalletms.service;

import com.example.authwalletms.dto.request.AuthTokenRequest;
import com.example.authwalletms.dto.request.LoginRequest;
import com.example.authwalletms.dto.request.OtpRequest;
import com.example.authwalletms.dto.request.SignUpRequest;
import com.example.authwalletms.dto.response.AuthResponse;

public interface AuthService {

    void signUp(SignUpRequest signUpRequest);

    AuthResponse login(LoginRequest loginRequest);

    void verifyAccount(OtpRequest request);

    void regenerateOtp(String email);

    AuthResponse refreshToken(AuthTokenRequest request);
}
