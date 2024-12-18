package com.example.authwalletms.controller;

import com.example.authwalletms.dto.request.AuthTokenRequest;
import com.example.authwalletms.dto.request.LoginRequest;
import com.example.authwalletms.dto.request.OtpRequest;
import com.example.authwalletms.dto.request.SignUpRequest;
import com.example.authwalletms.dto.response.AuthResponse;
import com.example.authwalletms.dto.response.BaseResponse;
import com.example.authwalletms.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("signUp")
    @ResponseStatus(CREATED)
    public BaseResponse<String> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return BaseResponse.success("Otp code sent to email. " +
                "Please verify your account");
    }

    @PostMapping("login")
    @ResponseStatus(OK)
    public BaseResponse<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return BaseResponse.oK(authService.login(loginRequest));
    }

    @PostMapping("verify-account")
    @ResponseStatus(OK)
    public BaseResponse<String> verifyAccount(@RequestBody @Valid OtpRequest request) {
        authService.verifyAccount(request);
        return BaseResponse.success("Your account have activated");
    }

    @PostMapping("regenerate-otp")
    @ResponseStatus(OK)
    public BaseResponse<String> regenerateOtp(@RequestParam String email) {
        authService.regenerateOtp(email);
        return BaseResponse.success("Otp code sent to email address");
    }

    @PostMapping("refresh-token")
    @ResponseStatus(OK)
    public BaseResponse<AuthResponse> refreshToken(@RequestBody @Valid AuthTokenRequest request) {
        return BaseResponse.oK(authService.refreshToken(request));
    }
}
