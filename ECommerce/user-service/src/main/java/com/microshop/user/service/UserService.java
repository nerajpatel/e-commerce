package com.microshop.user.service;

import com.microshop.user.dto.UserRegisterRequest;
import com.microshop.user.dto.UserResponse;
import com.microshop.user.model.Status;
import com.microshop.user.model.User;
import com.microshop.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .phone(request.phone())
                .active(true)
                .status(Status.ACTIVE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getStatus());
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getStatus());
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole(), u.getStatus()))
                .toList();
    }
}
