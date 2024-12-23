package com.example.authwalletms.service;

public interface EmailService {
    void sendOTPEmail(String toEmail, String otp);

    void consumeUserRegistration(String message);
}
