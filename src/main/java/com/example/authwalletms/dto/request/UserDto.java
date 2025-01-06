package com.example.authwalletms.dto.request;

public record UserDto(
        String email,
        String phoneNumber,

        String otp
) {
}
