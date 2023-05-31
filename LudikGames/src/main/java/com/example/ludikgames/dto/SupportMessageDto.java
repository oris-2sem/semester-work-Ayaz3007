package com.example.ludikgames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportMessageDto {
    private String content;
    private String sender;
    private String uuid;
}
