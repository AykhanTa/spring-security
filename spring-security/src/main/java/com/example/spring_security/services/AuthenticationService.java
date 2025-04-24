package com.example.spring_security.services;

import com.example.spring_security.dtos.JwtAuthenticationResponse;
import com.example.spring_security.dtos.RefreshTokenRequest;
import com.example.spring_security.dtos.SignInRequest;
import com.example.spring_security.dtos.SignUpRequest;
import com.example.spring_security.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
