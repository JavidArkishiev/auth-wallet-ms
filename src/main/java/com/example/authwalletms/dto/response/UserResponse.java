package com.example.authwalletms.dto.response;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String lastName,
        String firstName,
        String email,
        String phoneNumber

) {
}
