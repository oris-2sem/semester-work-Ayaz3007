package com.example.ludikgames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BJPlayerDto {
    private List<CardDto> cards;
    private Integer score;
    private String player;
}
