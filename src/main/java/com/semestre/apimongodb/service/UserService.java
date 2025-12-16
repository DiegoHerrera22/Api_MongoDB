package com.semestre.apimongodb.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.model.User;
import com.semestre.apimongodb.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        user.setRole(user.getRole() == null ? "ROLE_USER" : user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setNombre(user.getNombre());
            existingUser.setApellido(user.getApellido());
            existingUser.setRut(user.getRut());
            existingUser.setDireccion(user.getDireccion());
            existingUser.setRegion(user.getRegion());
            existingUser.setComuna(user.getComuna());
            existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existingUser.setRole(user.getRole() == null ? existingUser.getRole() : user.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted.";
    }
}