//package com.example.demo.service;
//
//import com.example.demo.dto.auth.AuthResponse;
//import com.example.demo.dto.auth.LoginRequest;
//import com.example.demo.dto.auth.RegisterRequest;
//import com.example.demo.entity.User;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface UserService {
//    AuthResponse register(RegisterRequest request);
//
//    AuthResponse login(LoginRequest request);
//
//    User getCurrentUser();
//
//    User getUserById(UUID id);
//
//    User getUserByUsername(String username);
//
//    List<User> getAllUsers();
//
////     Optional<User> getUserById(UUID id);
//
//    void deleteUser(UUID id);
//}




package com.example.demo.service;

import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    User getCurrentUser();

    Optional<User> getUserById(UUID id); // <-- updated

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void deleteUser(UUID id);
}
