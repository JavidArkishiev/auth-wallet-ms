package com.example.authwalletms.service;

public interface EmailService {
    void sendOTPEmail(String toEmail, String otp);
}
