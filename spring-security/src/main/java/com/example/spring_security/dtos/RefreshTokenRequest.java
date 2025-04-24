package com.example.spring_security.dtos;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
