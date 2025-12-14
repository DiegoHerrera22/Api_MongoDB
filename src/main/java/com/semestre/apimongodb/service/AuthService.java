package com.semestre.apimongodb.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.dto.AuthRequest;
import com.semestre.apimongodb.dto.AuthResponse;
import com.semestre.apimongodb.dto.RegisterRequest;
import com.semestre.apimongodb.dto.UserResponse;
import com.semestre.apimongodb.model.User;
import com.semestre.apimongodb.repository.UserRepository;
import com.semestre.apimongodb.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRegion(registerRequest.getRegion());
        user.setRole(resolveRole(registerRequest.getEmail()));

        User savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }

    public AuthResponse login(AuthRequest authRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtService.generateToken(user.getEmail(), user.getRole());
            return new AuthResponse(token);
        }
        throw new AuthenticationException("Authentication failed") {
            private static final long serialVersionUID = 1L;
        };
    }

    private String resolveRole(String email) {
        String normalizedEmail = email.toLowerCase();
        if (normalizedEmail.endsWith("@admin.com") || normalizedEmail.endsWith("@duocuc.cl")) {
            return "ROLE_ADMIN";
        }
        return "ROLE_USER";
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRegion(),
                user.getRole());
    }
}