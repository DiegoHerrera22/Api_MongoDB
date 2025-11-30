package com.semestre.apimongodb.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semestre.apimongodb.model.User;
import com.semestre.apimongodb.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        user.setId(UUID.randomUUID().toString().split("-")[0]);
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
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRegionId(user.getRegionId());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted.";
    }
}