package com.example.ludikgames.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @Size(min=6, max=20, message = "{registration.nickname.size}")
    @NotEmpty
    private String nickname;
    @Email
    @NotNull
    private String email;
    @Size(min=6, max=20, message = "{registration.password.size}")
    @NotEmpty
    private String password;
}
