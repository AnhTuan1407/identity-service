package com.tuanha.identity.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tuanha.identity.constant.PredefinedPermission;
import com.tuanha.identity.constant.PredefinedRole;
import com.tuanha.identity.model.Permission;
import com.tuanha.identity.model.Role;
import com.tuanha.identity.model.User;
import com.tuanha.identity.repository.IPermissionRepository;
import com.tuanha.identity.repository.IRoleRepository;
import com.tuanha.identity.repository.IUserRepository;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    @NonNull
    private final static String ADMIN_USERNAME = "admin";

    @NonNull
    private final static String ADMIN_PASSWORD = "admin";
    
    PasswordEncoder passwordEncoder;

    @Bean
    // @ConditionalOnProperty(
    //         prefix = "spring",
    //         value = "datasource.driverClassName",
    //         havingValue = "com.mysql.cj.jdbc.Driver") // Only run this bean if the driver is MySQL
    ApplicationRunner applicationRunner(IUserRepository userRepository, IRoleRepository roleRepository, IPermissionRepository permissionRepository) {

        log.info("Initializing application.....");

        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {

                String[] permissions = PredefinedPermission.ALL_PERMISSIONS;

                for (String permission : permissions) {
                    permissionRepository.save(Permission.builder()
                        .name(permission)
                        .description(permission)
                        .build());
                }

                HashSet<Permission> adminPermissions = new HashSet<>();
                for (String permission : permissions) {
                    var permissionEntity = Permission.builder()
                        .name(permission)
                        .description(permission)
                        .build();
                    adminPermissions.add(permissionEntity);
                }

                String[] userPrefinedPermissions = PredefinedPermission.USER_PERMISSIONS;
                HashSet<Permission> userPermissions = new HashSet<>();
                for (String permission : userPrefinedPermissions) {
                    var permissionEntity = Permission.builder()
                        .name(permission)
                        .description(permission)
                        .build();
                        userPermissions.add(permissionEntity);
                }


                roleRepository.save(Role.builder()
                    .name(PredefinedRole.ROLE_USER)
                    .description("User role")
                    .permissions(userPermissions)
                    .build());

                var roleAdmin = roleRepository.save(Role.builder()
                    .name(PredefinedRole.ROLE_ADMIN)
                    .description("Admin role")
                    .permissions(adminPermissions)
                    .build());

                HashSet<Role> roles = new HashSet<>();
                roles.add(roleAdmin);

                User user = User.builder()
                    .username(ADMIN_USERNAME)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .roles(roles)
                    .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it immediately");
            }

            log.info("Application initialization completed .....");
        };
    }
}
