package com.noobprogrammer.chatterbox.services;

import com.noobprogrammer.chatterbox.dto.UserRequest;
import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.models.User;
import com.noobprogrammer.chatterbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserRequest userRequest) {

        log.info("Entering UserService.registerUser()");

        User newUser = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .build();
        userRepository.save(newUser);
        log.info("User details saved successfully: {}", newUser.getUsername());
        log.info("Exiting UserService.registerUser()");
    }

    public void loginUser(UserRequest userRequest) throws UserNotFoundException {

        log.info("Entering UserService.loginUser()");
        User user = userRepository.findByUsername(userRequest.getUsername());

        if(user.getUsername().equals(userRequest.getUsername())
                && userRequest.getPassword().equals(user.getPassword())) {
            log.info("User found: {}", user.getUsername());
            log.info("User logged in successfully");
        } else{
            log.error("User not found: {}", userRequest.getUsername());
            throw new UserNotFoundException();
        }


    }
}