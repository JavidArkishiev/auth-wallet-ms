package com.example.authwalletms.service.impl;

import com.example.authwalletms.dto.request.RoleRequest;
import com.example.authwalletms.dto.response.RoleResponse;
import com.example.authwalletms.exception.ResourceExistException;
import com.example.authwalletms.exception.ResourceFoundException;
import com.example.authwalletms.model.Role;
import com.example.authwalletms.repository.RoleRepository;
import com.example.authwalletms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void createRole(RoleRequest roleRequest) {
        roleRepository.findByName(roleRequest.name())
                .ifPresentOrElse(
                        existingRole -> {
                            throw new ResourceExistException("Role already exists: " + roleRequest.name());
                        },
                        () -> {
                            Role role = new Role();
                            role.setName(roleRequest.name());
                            role.setCreateAt(LocalDateTime.now());
                            roleRepository.save(role);
                        }
                );
    }

    @Override
    public List<RoleResponse> getAllRole() {
        var roleList = roleRepository.findAll();
        if (roleList.isEmpty()) {
            return Collections.emptyList();
        }
        return roleList.stream()
                .map(role -> new RoleResponse(
                        role.getName(),
                        role.getCreateAt()
                )).toList();
    }
}

