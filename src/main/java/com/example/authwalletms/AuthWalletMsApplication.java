package com.example.authwalletms;

import com.example.authwalletms.model.Role;
import com.example.authwalletms.model.User;
import com.example.authwalletms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthWalletMsApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String adminEmail = "admin@gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(AuthWalletMsApplication.class, args);

    }

    public void crateAdmin() {
        if (!userRepository.existsByEmail(adminEmail)) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setLastName("Admin");
            user.setFirstName("Admin");
            user.setPhoneNumber("+994558528873");
            user.setCreateAt(LocalDateTime.now());
            user.setOtp(null);
            user.setOtpCreateTime(null);
            user.setEnable(true);
            user.setPassword(passwordEncoder.encode("admin1234"));

            Role role = new Role();
            role.setName("ADMIN");
            role.setCreateAt(LocalDateTime.now());
            role.setUpdateAt(null);
            user.setRoles(Set.of(role));
            userRepository.save(user);


        }

    }

    @Override
    public void run(String... args) {
        crateAdmin();
    }


}
