package com.example.authwalletms.mapper;

import com.example.authwalletms.dto.request.SignUpRequest;
import com.example.authwalletms.exception.ResourceFoundException;
import com.example.authwalletms.model.Role;
import com.example.authwalletms.model.User;
import com.example.authwalletms.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpRequest.password()))")
    @Mapping(target = "otp", expression = "java(createOtp())")
    @Mapping(target = "roles", expression = "java(createUserRole())")
    @Mapping(target = "phoneNumber", expression = "java(formatPhoneNumber(signUpRequest.phoneNumber()))")
    @Mapping(target = "email", expression = "java(signUpRequest.email())")
    public abstract User mapToEntity(SignUpRequest signUpRequest);

    public String createOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    public String formatPhoneNumber(String phoneNumber) {
        String cleanedPhone = phoneNumber.replaceAll("\\s+", "");
        if (!cleanedPhone.startsWith("+994")) {
            cleanedPhone = "+994" + cleanedPhone;
        }
        return cleanedPhone;
    }


    public Set<Role> createUserRole() {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceFoundException("User role not found"));

        Set<Role> roleList = new HashSet<>();
        roleList.add(role);
        return roleList;

    }

}
