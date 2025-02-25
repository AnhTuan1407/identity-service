package com.tuanha.identity.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.tuanha.identity.dto.request.AuthenticationRequest;
import com.tuanha.identity.dto.response.AuthenticationResponse;
import com.tuanha.identity.dto.request.IntrospectRequest;
import com.tuanha.identity.dto.response.IntrospectResponse;

public interface IAuthenticationService {
    AuthenticationResponse authenticated(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException ;
}
