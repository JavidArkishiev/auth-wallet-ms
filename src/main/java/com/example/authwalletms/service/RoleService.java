package com.example.authwalletms.service;

import com.example.authwalletms.dto.request.RoleRequest;
import com.example.authwalletms.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    void createRole(RoleRequest roleRequest);

    List<RoleResponse> getAllRole();
}
