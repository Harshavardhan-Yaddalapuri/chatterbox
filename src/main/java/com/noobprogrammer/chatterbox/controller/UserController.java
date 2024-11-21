package com.noobprogrammer.chatterbox.controller;

import com.noobprogrammer.chatterbox.dto.UserRequest;
import com.noobprogrammer.chatterbox.dto.UserResponse;
import com.noobprogrammer.chatterbox.exceptions.UserAlreadyExistsException;
import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        log.info("Entering UserController.registerUser method with userRequest: {}", userRequest);
        userService.registerUser(userRequest);
        log.info("Exiting UserController.registerUser method");
    }

    @PostMapping("/login")
    public void loginUser(@RequestBody UserRequest userRequest) throws UserNotFoundException {
        log.info("Entering UserController.loginUser method with userRequest: {}", userRequest);
        userService.loginUser(userRequest);
        log.info("Exiting UserController.loginUser method");
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        log.info("Entering UserController.getAllUsers method");
        List<UserResponse> users = userService.getAllUsers();
        log.info("Exiting UserController.getAllUsers method with users: {}", users);
        return users;
    }
}