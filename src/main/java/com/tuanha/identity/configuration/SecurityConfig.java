package com.tuanha.identity.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.tuanha.identity.enums.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = {"/auth/token", "/auth/introspect", "/users", "/users/myInfo", "/auth/logout"};

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(
            (authorizeRequest) -> authorizeRequest.requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS).permitAll()
                // .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2ResourceServer(
            oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
            )
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
