package com.example.spring_security.services.impl;

import com.example.spring_security.dtos.SignUpRequest;
import com.example.spring_security.entities.Role;
import com.example.spring_security.entities.User;
import com.example.spring_security.repository.UserRepository;
import com.example.spring_security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest signUpRequest){
        var existUser=userRepository.findByEmail(signUpRequest.getEmail());
        if(existUser.isPresent()){
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user=new User();

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }
}
