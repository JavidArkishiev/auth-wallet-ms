package com.example.authwalletms.controller;

import com.example.authwalletms.dto.request.RoleRequest;
import com.example.authwalletms.dto.response.BaseResponse;
import com.example.authwalletms.dto.response.RoleResponse;
import com.example.authwalletms.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(CREATED)
    public BaseResponse<String> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        roleService.createRole(roleRequest);
        return BaseResponse.success("new Role created");

    }

    @GetMapping
    @ResponseStatus(OK)
    public BaseResponse<List<RoleResponse>> getAllRole() {
        return BaseResponse.oK(roleService.getAllRole());
    }
}
