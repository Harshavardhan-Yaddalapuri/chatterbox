package com.noobprogrammer.chatterbox.services;

import com.noobprogrammer.chatterbox.exceptions.UserNotFoundException;
import com.noobprogrammer.chatterbox.models.FriendRequest;
import com.noobprogrammer.chatterbox.models.User;
import com.noobprogrammer.chatterbox.repository.FriendRequestRepository;
import com.noobprogrammer.chatterbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    // Method to search for users by username
    public List<User> searchUsers(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    public void sendFriendRequest(String senderId, String receiverId) {
        log.info("User {} is sending a friend request to user {}", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            log.error("Users cannot send friend requests to themselves");
            throw new IllegalArgumentException("Users cannot send friend requests to themselves");
        }

        User sender = userRepository.findByUsername(senderId)
                .orElseThrow(() -> {
                    log.error("Sender not found: {}", senderId);
                    return new UserNotFoundException("Sender not found: " + senderId);
                });

        User receiver = userRepository.findByUsername(receiverId)
                .orElseThrow(() -> {
                    log.error("Receiver not found: {}", receiverId);
                    return new UserNotFoundException("Receiver not found: " + receiverId);
                });

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        friendRequestRepository.save(friendRequest);
        log.info("Friend request sent from {} to {}", sender.getUsername(), receiver.getUsername());
    }

    // Method to search for users by username
    public List<FriendRequest> getPendingRequests(Long userId) {
        return friendRequestRepository.findByReceiverIdAndAcceptedFalse(userId);
    }

    // Method to accept a friend request
    public void acceptFriendRequest(Long requestId){
    FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
    request.setAccepted(true);
    friendRequestRepository.save(request);
    }
}
