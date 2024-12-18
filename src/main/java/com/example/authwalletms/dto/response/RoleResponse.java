package com.example.authwalletms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RoleResponse(
        String roleName,
        @JsonFormat(pattern = "d, MMMM yyyy hh:mm:ss a")
        LocalDateTime createAt
) {
}
