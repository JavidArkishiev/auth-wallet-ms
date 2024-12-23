package com.example.authwalletms.service.impl;

import com.example.authwalletms.dto.request.AuthTokenRequest;
import com.example.authwalletms.dto.request.LoginRequest;
import com.example.authwalletms.dto.request.OtpRequest;
import com.example.authwalletms.dto.request.SignUpRequest;
import com.example.authwalletms.dto.response.AuthResponse;
import com.example.authwalletms.exception.ResourceExistException;
import com.example.authwalletms.exception.ResourceFoundException;
import com.example.authwalletms.mapper.UserMapper;
import com.example.authwalletms.model.User;
import com.example.authwalletms.repository.UserRepository;
import com.example.authwalletms.service.AuthService;
import com.example.authwalletms.service.EmailService;
import com.example.authwalletms.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new ResourceExistException("This email already exist");
        }
        String updatePhoneNumber = userMapper.formatPhoneNumber(signUpRequest.phoneNumber());

        if (userRepository.existsByPhoneNumber(updatePhoneNumber)) {
            throw new ResourceExistException("This phoneNumber already exist");

        }
        if (!signUpRequest.password().equals(signUpRequest.confirmPassword())) {
            throw new ResourceExistException("Both of the password must same");
        }
        User user = userMapper.mapToEntity(signUpRequest);
        user.setCreateAt(LocalDateTime.now());
        user.setOtpCreateTime(LocalDateTime.now());
        kafkaTemplate.send("user-register", user.getEmail() + ":" + user.getOtp());
        userRepository.save(user);

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String updatePhoneNumber = userMapper.formatPhoneNumber(loginRequest.phoneNumber());

        User user = userRepository.findByPhoneNumber(updatePhoneNumber)
                .orElseThrow(() -> new ResourceFoundException("User not found or password incorrect"));
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ResourceFoundException("User not found or password incorrect");
        }
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(updatePhoneNumber,
                        loginRequest.password()));
        if (!user.isEnable()) {
            throw new ResourceExistException("Your account is not active");

        }

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    @Transactional
    public void verifyAccount(OtpRequest request) {
        var user = userRepository.findByOtp(request.otp())
                .orElseThrow(() -> new ResourceFoundException("otp can not found or incorrect"));
        if (user.isEnable()) {
            throw new ResourceExistException("Your account already verify");
        }
        if (Duration.between(user.getOtpCreateTime(),
                LocalDateTime.now()).getSeconds() > 180) {
            throw new ResourceExistException("Otp time is over ." +
                    "PLease regenerate otp again");
        }
        user.setEnable(true);
        user.setOtp(null);
        user.setOtpCreateTime(null);
        user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);
        kafkaTemplate.send("verified-user", user.getPhoneNumber());
    }

    @Override
    public void regenerateOtp(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found"));
        String otp = userMapper.createOtp();
        user.setOtp(otp);
        user.setOtpCreateTime(LocalDateTime.now());
        emailService.sendOTPEmail(email, otp);
        userRepository.save(user);

    }

    @Override
    public AuthResponse refreshToken(AuthTokenRequest request) {
        var email = jwtService.extractUsername(request.refreshToken());

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found"));

        if (jwtService.isTokenValid(request.refreshToken(), user)) {
            var accessToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return null;
    }
}
