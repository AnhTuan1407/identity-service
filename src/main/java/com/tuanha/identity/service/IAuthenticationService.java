package com.tuanha.identity.service;

import com.tuanha.identity.dto.request.AuthenticationRequest;

public interface IAuthenticationService {
    boolean authenticated(AuthenticationRequest request);
}
