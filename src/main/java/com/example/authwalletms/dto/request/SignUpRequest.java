package com.example.authwalletms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @NotBlank(message = "email can not be null")
        @Email(message = "email format is not true")
        String email,
        @NotBlank(message = "phoneNumber can not be null")
        @Schema(example = "51 789 89 89")
        String phoneNumber,
        @NotBlank(message = "Password can not be null")
        @Schema(example = "string")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$",
                message = "Password must be at least 6 characters long and contain at least one uppercase and one lowercase letter"
        )
        String password,

        @NotBlank(message = "confirmPassword can not be null")
        String confirmPassword

) {
}
