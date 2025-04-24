package com.example.spring_security.dtos;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
