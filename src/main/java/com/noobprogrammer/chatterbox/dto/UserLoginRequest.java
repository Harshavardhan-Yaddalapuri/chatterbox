package com.noobprogrammer.chatterbox.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    @NotNull(message = "Username is required.")
    private String username;

    @NotNull(message = "Username is required.")
    private String password;
}
