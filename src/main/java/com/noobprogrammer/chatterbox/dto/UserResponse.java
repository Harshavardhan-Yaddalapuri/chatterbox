package com.noobprogrammer.chatterbox.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Id
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
