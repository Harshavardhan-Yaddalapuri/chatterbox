package com.noobprogrammer.chatterbox.controller;

import com.noobprogrammer.chatterbox.dto.UserLoginRequest;
import com.noobprogrammer.chatterbox.dto.UserRequest;
import com.noobprogrammer.chatterbox.dto.UserResponse;
import com.noobprogrammer.chatterbox.exceptions.UserAlreadyExistsException;
import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

   private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest userRequest) {

        log.info("Entering UserController.registerUser method");
        try {
            userService.registerUser(userRequest);
            log.info("Exiting UserController.registerUser method ### User ==> {} registered successfully", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (UserAlreadyExistsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists.");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) throws UserNotFoundException {

        log.info("Entering UserController.loginUser method");
        try {
            userService.loginUser(userLoginRequest);
            return ResponseEntity.ok("Login successful.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        log.info("Entering UserController.getAllUsers method");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //TODO: Implement the method to get a user by username or userId
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        log.info("Entering UserController.getUserById method");
        UserResponse user = userService.getUserbyUserId(id);
        log.info("Exiting UserController.getUserById method ### User ==> {} retrieved successfully", user.getUsername());
        return ResponseEntity.ok(user);
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username){
        log.info("Entering UserController.getUserByUsername method");
        UserResponse user = userService.getUserbyUsername(username);
        log.info("Exiting UserController.getUserByUsername method ### User ==> {} retrieved successfully", user.getUsername());
        return ResponseEntity.ok(user);
    }

    //TODO: Implement the method to update a user's details
    //TODO: Implement the method to delete a user
}
