package com.noobprogrammer.chatterbox.repository;

import com.noobprogrammer.chatterbox.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverIdAndAcceptedFalse(long receiverId); // Get pending requests for a user

}
