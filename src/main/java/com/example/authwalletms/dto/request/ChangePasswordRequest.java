package com.example.authwalletms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank(message = "Current password can not be null")
        String currentPassword,
        @NotBlank(message = "New password can not be null")
        @Schema(example = "string")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$",
                message = "Password must be at least 6 characters long and contain at least one uppercase and one lowercase letter"
        )
        String newPassword,
        @NotBlank(message = "Confirm new password can not be null")

        String confirmNewPassword
) {
}
