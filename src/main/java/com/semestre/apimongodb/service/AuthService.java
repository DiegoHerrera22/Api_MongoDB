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

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRegionId(registerRequest.getRegionId());
        user.setRole(registerRequest.getRole() == null ? "ROLE_USER" : registerRequest.getRole());
        return userRepository.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            return new AuthResponse(token);
        }
        throw new AuthenticationException("Authentication failed") {
            private static final long serialVersionUID = 1L;
        };
    }
}