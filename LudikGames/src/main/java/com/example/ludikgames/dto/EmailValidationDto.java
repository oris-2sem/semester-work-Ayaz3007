package com.example.ludikgames.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailValidationDto {
    private String status;
    private String reason;
}
