package com.example.ludikgames.dto.slots;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinResultDto {
    private Integer balance;
    private Integer wonMoney;
    private List<Integer> wonSymbols;
}
