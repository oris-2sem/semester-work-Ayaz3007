package com.example.ludikgames.dto.slots;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpinResultDto {
    private String result;
    private Integer balance;
}
