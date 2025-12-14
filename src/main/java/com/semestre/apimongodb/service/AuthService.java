package com.semestre.apimongodb.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.semestre.apimongodb.dto.AuthRequest;
import com.semestre.apimongodb.dto.AuthResponse;
import com.semestre.apimongodb.dto.RegisterRequest;
import com.semestre.apimongodb.dto.UserResponse;
import com.semestre.apimongodb.model.Region;
import com.semestre.apimongodb.model.User;
import com.semestre.apimongodb.repository.RegionRepository;
import com.semestre.apimongodb.repository.UserRepository;
import com.semestre.apimongodb.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RegionRepository regionRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya se encuentra registrado");
        }

        Region region = resolveRegion(registerRequest.getRegion());
        validateComuna(region, registerRequest.getComuna());

        User user = new User();
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        user.setNombre(registerRequest.getNombre());
        user.setApellido(registerRequest.getApellido());
        user.setRut(registerRequest.getRut());
        user.setDireccion(registerRequest.getDireccion());
        user.setRegion(region);
        user.setComuna(registerRequest.getComuna());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(resolveRole(registerRequest.getEmail()));

        User savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }

    public AuthResponse login(AuthRequest authRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
            String token = jwtService.generateToken(user.getEmail(), user.getRole());
            return new AuthResponse(token);
        }
        throw new AuthenticationException("Authentication failed") {
            private static final long serialVersionUID = 1L;
        };
    }

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        if (registerRequest.getRegion() == null || registerRequest.getRegion().getId() == null
                || registerRequest.getRegion().getId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La región es obligatoria");
        }
        if (isBlank(registerRequest.getNombre()) || isBlank(registerRequest.getApellido())
                || isBlank(registerRequest.getRut()) || isBlank(registerRequest.getDireccion())
                || isBlank(registerRequest.getComuna()) || isBlank(registerRequest.getEmail())
                || isBlank(registerRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltan datos obligatorios");
        }
    }

    private void validateComuna(Region region, String comuna) {
        if (region.getComunas() == null || !region.getComunas().contains(comuna)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La comuna no pertenece a la región indicada");
        }
    }

    private Region resolveRegion(Region requestRegion) {
        return regionRepository.findById(requestRegion.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Región inválida"));
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
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
                user.getNombre(),
                user.getApellido(),
                user.getRut(),
                user.getDireccion(),
                user.getRegion(),
                user.getComuna(),
                user.getEmail(),
                user.getRole());
    }
}