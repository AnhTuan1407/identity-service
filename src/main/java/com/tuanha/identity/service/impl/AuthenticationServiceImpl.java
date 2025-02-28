package com.tuanha.identity.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tuanha.identity.dto.request.AuthenticationRequest;
import com.tuanha.identity.dto.request.IntrospectRequest;
import com.tuanha.identity.dto.request.LogoutRequest;
import com.tuanha.identity.dto.response.AuthenticationResponse;
import com.tuanha.identity.dto.response.IntrospectResponse;
import com.tuanha.identity.exception.AppException;
import com.tuanha.identity.exception.ErrorCode;
import com.tuanha.identity.model.InvalidatedToken;
import com.tuanha.identity.model.User;
import com.tuanha.identity.repository.IInvalidatedTokenRepository;
import com.tuanha.identity.repository.IUserRepository;
import com.tuanha.identity.service.IAuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {

    IUserRepository userRepository;

    IInvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        
        var token = request.getToken();

        boolean isValid = true;
        
        try {
            verifyToken(token);

        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
        .valid(isValid)
        .build();
    }

    @Override
    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
        .token(token)
        .authenticated(authenticated)
        .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issuer("https://tuanha.com")
            .issueTime(new Date())
            .expirationTime(new Date(
                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
            ))
            .claim("scope", buildScope(user))
            .jwtID(UUID.randomUUID().toString())
            .build();

            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);

            try {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
                return jwsObject.serialize();
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                joiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        joiner.add(permission.getName());
                    });
                }
            });
        }

        return joiner.toString();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException {
        var token = request.getToken();

        SignedJWT signedJWT = SignedJWT.parse(token);

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
            .id(signedJWT.getJWTClaimsSet().getJWTID())
            .expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
            .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        var verified = signedJWT.verify(verifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if(!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

}
