package com.example.authwalletms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteRequest(
        @NotBlank(message = "Current password can not be null")
        String currentPassword
) {
}
