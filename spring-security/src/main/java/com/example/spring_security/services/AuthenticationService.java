package com.example.spring_security.services;

import com.example.spring_security.dtos.SignUpRequest;
import com.example.spring_security.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
}
