package com.example.authwalletms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "phoneNumber can not be null")
        @Schema(example = "51 789 89 89")
        String phoneNumber,
        @NotBlank(message = "password can not be null")
        String password
) {
}
