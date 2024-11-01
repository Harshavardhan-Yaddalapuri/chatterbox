package com.noobprogrammer.chatterbox.services;

import com.noobprogrammer.chatterbox.dto.UserRequest;
import com.noobprogrammer.chatterbox.dto.UserResponse;
import com.noobprogrammer.chatterbox.exceptions.UserAlreadyExistsException;
import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.models.User;
import com.noobprogrammer.chatterbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRequest userRequest) throws UserAlreadyExistsException {

        log.info("Entering UserService.registerUser()");
        if (userRepository.existsByUsername(userRequest.getUsername()) || userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        User newUser = User.builder()
                .username(userRequest.getUsername())
                .password(encryptedPassword)
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .build();

        userRepository.save(newUser);
        log.info("User registered successfully: {}", newUser.getUsername());
    }

    public void loginUser(UserRequest userRequest) throws UserNotFoundException {

        log.info("Entering UserService.loginUser()");
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }

        log.info("User logged in successfully: {}", user.getUsername());

    }


    public List<UserResponse> getAllUsers() {

        log.info("Entering UserService.getAllUsers()");
        List<User> users = userRepository.findAll();
        log.info("Users found: {}", users.size());
        return users.stream().map(this::mapToUserResponse).toList();

    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder().id(user.getId()).username(user.getUsername()).firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).build();
    }
}
