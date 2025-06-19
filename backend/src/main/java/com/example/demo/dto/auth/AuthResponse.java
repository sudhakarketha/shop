package com.example.demo.dto.auth;

import com.example.demo.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UUID id;
    private String username;
    private String email;
    private UserRole role;
}