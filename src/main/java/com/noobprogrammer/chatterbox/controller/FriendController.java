package com.noobprogrammer.chatterbox.controller;

import com.noobprogrammer.chatterbox.models.FriendRequest;
import com.noobprogrammer.chatterbox.models.User;
import com.noobprogrammer.chatterbox.services.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@Slf4j
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/search")
    public List<User> serachUsers(@RequestParam String username) {
        return friendService.searchUsers(username);
    }

    @PostMapping("/requests")
    public void sendFriendRequest(@RequestParam String senderUsername, @RequestParam String receiverUsername) {
        log.info("Inside sendFriendRequest");
        friendService.sendFriendRequest(senderUsername, receiverUsername);
    }

    @GetMapping("/requests/pending")
    public List<FriendRequest> getPendingRequests(@RequestParam Long userId) {
        return friendService.getPendingRequests(userId);
    }

    @PostMapping("/requests/accept")
    public void acceptFriendRequest(@RequestParam Long requestId) {
        friendService.acceptFriendRequest(requestId);
    }
}
