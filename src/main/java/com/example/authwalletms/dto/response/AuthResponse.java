package com.example.authwalletms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken


) {
}
