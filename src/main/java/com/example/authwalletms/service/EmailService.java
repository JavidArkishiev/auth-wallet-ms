package com.example.authwalletms.service;

import com.example.authwalletms.dto.request.UserDto;
import com.example.authwalletms.model.User;

public interface EmailService {
    void sendOTPEmail(String toEmail, String otp);

    void consumeUserRegistration(UserDto user);
}
