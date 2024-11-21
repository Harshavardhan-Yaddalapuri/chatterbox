package com.noobprogrammer.chatterbox.services;

import com.noobprogrammer.chatterbox.dto.UserLoginRequest;
import com.noobprogrammer.chatterbox.dto.UserRequest;
import com.noobprogrammer.chatterbox.dto.UserResponse;
import com.noobprogrammer.chatterbox.dto.UserUpdateRequest;
import com.noobprogrammer.chatterbox.exceptions.UserAlreadyExistsException;
import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.models.User;
import com.noobprogrammer.chatterbox.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        User newUser = User.builder().username(userRequest.getUsername()).password(encryptedPassword).firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).email(userRequest.getEmail()).build();

        userRepository.save(newUser);
        log.info("User registered successfully: {}", newUser.getUsername());
    }

    public void loginUser(UserLoginRequest userLoginRequest) throws UserNotFoundException {

        log.info("Entering UserService.loginUser()");
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow(() -> new UserNotFoundException("Invalid username or password"));


        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {

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


    public UserResponse getUserbyUserId(Long id) {
        log.info("Entering UserService.getUserbyUserId()");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("User found with id: {}", user.get().getId());
            return mapToUserResponse(user.get());
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public UserResponse getUserbyUsername(String username) {
        log.info("Entering UserService.getUserbyUsername()");
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            log.info("User found with username: {}", user.get().getUsername());
            return mapToUserResponse(user.get());
        } else {
            throw new UserNotFoundException("User not found with username: " + username);
        }
    }

    public void updateUser(Long id, @Valid UserUpdateRequest userUpdateRequest) {
        log.info("Entering UserService.updateUser method");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            log.info("Update user with user ID: {}", user.get().getId());
            user.get().setFirstName(userUpdateRequest.getFirstName());
            user.get().setLastName(userUpdateRequest.getLastName());

            userRepository.save(user.get());
            log.info("User updated successfully: {}", user.get().getUsername());
        }else
            throw new UserNotFoundException("Couldn't update, user not found with id: " + id);
    }

    public void deleteUser(Long id) {
        log.info("Entering UserService.deleteUser method");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            log.info("delete user with user ID: {}", user.get().getId());
            userRepository.delete(user.get());
            log.info("User deleted successfully: {}", user.get().getUsername());
        }else
            throw new UserNotFoundException("Couldn't delete, user not found with id: " + id);
    }
}
