package com.example.authwalletms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank String name

) {
}
