package com.example.authwalletms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank
        String name,
        @NotBlank
        String surname
) {
}
