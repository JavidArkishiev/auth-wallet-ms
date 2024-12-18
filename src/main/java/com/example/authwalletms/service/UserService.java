package com.example.authwalletms.service;

import com.example.authwalletms.dto.request.ChangePasswordRequest;
import com.example.authwalletms.dto.request.DeleteRequest;
import com.example.authwalletms.dto.request.UserUpdateRequest;
import com.example.authwalletms.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(int page, int size);

    UserResponse getProfile();

    void updateProfile(UserUpdateRequest request);

    void changePassword(ChangePasswordRequest request);

    void deleteProfile(DeleteRequest request);

    UserResponse getProfileByPhoneNumber(String phoneNumber);
}
