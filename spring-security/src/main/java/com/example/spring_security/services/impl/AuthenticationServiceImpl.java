package com.example.spring_security.services.impl;

import com.example.spring_security.dtos.JwtAuthenticationResponse;
import com.example.spring_security.dtos.RefreshTokenRequest;
import com.example.spring_security.dtos.SignInRequest;
import com.example.spring_security.dtos.SignUpRequest;
import com.example.spring_security.entities.Role;
import com.example.spring_security.entities.User;
import com.example.spring_security.repository.UserRepository;
import com.example.spring_security.services.AuthenticationService;
import com.example.spring_security.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest){
        logger.info("SignUp new user: {}", signUpRequest.getEmail());
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

        userRepository.save(user);
        logger.info("User added with ID: {}", user.getId());
        return user;
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
        logger.info("SignIn user: {}", signInRequest.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                        signInRequest.getPassword()));
        var user=userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt =jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        logger.info("JWT token and refreshToken generated successfully");
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        logger.info("Updating  access token for refresh token");
        String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
        var user=userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt=jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            logger.info("Access token generated successfully");
            return jwtAuthenticationResponse;
        }
        logger.error("Invalid refresh token");
        return null;
    }
}
