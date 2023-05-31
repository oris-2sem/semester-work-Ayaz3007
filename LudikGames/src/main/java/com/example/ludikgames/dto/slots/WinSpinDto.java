package com.example.ludikgames.dto.slots;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WinSpinDto {
    private Integer stake;
    private List<Integer> symbols;
}
