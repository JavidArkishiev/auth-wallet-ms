package com.example.authwalletms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OtpRequest(
        @NotBlank(message = "otp can not be null")
        @Size(min = 4, max = 4)
        String otp

) {
}
