package com.example.authwalletms.controller;

import com.example.authwalletms.dto.request.ChangePasswordRequest;
import com.example.authwalletms.dto.request.DeleteRequest;
import com.example.authwalletms.dto.request.UserUpdateRequest;
import com.example.authwalletms.dto.response.BaseResponse;
import com.example.authwalletms.dto.response.UserResponse;
import com.example.authwalletms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(OK)
    public BaseResponse<List<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return BaseResponse.oK(userService.getAllUsers(page, size));

    }

    @GetMapping("profile")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public BaseResponse<UserResponse> getProfile() {
        return BaseResponse.oK(userService.getProfile());
    }

    @PutMapping("profile")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(OK)
    public BaseResponse<String> updateProfile(@RequestBody @Valid UserUpdateRequest request) {
        userService.updateProfile(request);
        return BaseResponse.success("OK");
    }

    @PatchMapping("change-password")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(OK)
    public BaseResponse<String> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
        return BaseResponse.success("OK");
    }

    @DeleteMapping("delete-profile")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(OK)
    public BaseResponse<String> deleteProfile(@RequestBody @Valid DeleteRequest request) {
        userService.deleteProfile(request);
        return BaseResponse.success("Your account have deleted");
    }

    @GetMapping("profile/{phoneNumber}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    public BaseResponse<UserResponse> getProfileByPhoneNumber(@PathVariable String phoneNumber) {
        return BaseResponse.oK(userService.getProfileByPhoneNumber(phoneNumber));
    }

}
