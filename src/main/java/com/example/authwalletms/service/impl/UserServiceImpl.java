package com.example.authwalletms.service.impl;

import com.example.authwalletms.dto.request.ChangePasswordRequest;
import com.example.authwalletms.dto.request.DeleteRequest;
import com.example.authwalletms.dto.request.UserUpdateRequest;
import com.example.authwalletms.dto.response.UserResponse;
import com.example.authwalletms.exception.ResourceExistException;
import com.example.authwalletms.exception.ResourceFoundException;
import com.example.authwalletms.repository.UserRepository;
import com.example.authwalletms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        var users = userRepository.findAll(pageable);
        return users.stream()
                .map(user -> new UserResponse(
                        user.getUserId(),
                        user.getLastName(),
                        user.getFirstName(),
                        user.getEmail(),
                        user.getPhoneNumber()
                )).toList();
    }

    @Override
    public UserResponse getProfile() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found with email: " + email));
        return new UserResponse(
                user.getUserId(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    @Override
    public void updateProfile(UserUpdateRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found with email: " + email));
        user.setFirstName(request.name());
        user.setLastName(request.surname());
        user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);

    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found with email: " + email));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ResourceExistException("Current password incorrect");
        }
        if (!request.newPassword().equals(request.confirmNewPassword())) {
            throw new ResourceExistException("Both of password must same");
        }
        user.setPassword(passwordEncoder.encode(request.confirmNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteProfile(DeleteRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceFoundException("User not found with email: " + email));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ResourceExistException("Current password incorrect");

        }
        userRepository.delete(user);

    }

    @Override
    public UserResponse getProfileByPhoneNumber(String phoneNumber) {
        var user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceFoundException("User not found with this " + phoneNumber));
        return new UserResponse(
                user.getUserId(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
