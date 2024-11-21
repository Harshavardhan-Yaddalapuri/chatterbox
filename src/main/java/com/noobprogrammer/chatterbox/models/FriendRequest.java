package com.noobprogrammer.chatterbox.models;

import com.noobprogrammer.chatterbox.utils.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_friend_request")
@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING; // Tracks the status of the request accepted

    @Column
    private LocalDateTime createddt;

    @Column
    private LocalDateTime updateddt;

    @PrePersist
    protected void onCreate() {
        createddt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updateddt = LocalDateTime.now();
    }
}
