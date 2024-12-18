package com.example.authwalletms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthTokenRequest(
        @NotBlank(message = "refreshToken can not ve null")
        String refreshToken
) {
}
