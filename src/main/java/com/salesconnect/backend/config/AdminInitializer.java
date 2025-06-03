package com.salesconnect.backend.config;

import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.entity.enums.Role;
import com.salesconnect.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si un admin global existe déjà
        if (!userRepository.existsByRole(Role.ADMIN_GLOBAL)) {
            User globalAdmin = User.builder()
                    .firstName("Admin")
                    .lastName("Global")
                    .email("admin@salesconnect.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN_GLOBAL)
                    .build();

            userRepository.save(globalAdmin);
            System.out.println("Admin global créé avec succès !");
        }
    }
}

