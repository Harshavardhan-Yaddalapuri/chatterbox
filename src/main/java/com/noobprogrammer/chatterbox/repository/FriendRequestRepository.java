package com.noobprogrammer.chatterbox.repository;

import com.noobprogrammer.chatterbox.models.FriendRequest;
import com.noobprogrammer.chatterbox.utils.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverIdAndStatus(long receiverId, RequestStatus status); // Get pending requests for a user

}
