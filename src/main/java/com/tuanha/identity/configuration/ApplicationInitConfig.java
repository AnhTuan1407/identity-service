package com.tuanha.identity.configuration;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tuanha.identity.enums.Role;
import com.tuanha.identity.model.User;
import com.tuanha.identity.repository.IUserRepository;

import lombok.AccessLevel;

@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                HashSet<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("Admin")
                    .lastName("Admin")
                    // .roles(roles)
                    .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it immediately");
            }
        };
    }
}
