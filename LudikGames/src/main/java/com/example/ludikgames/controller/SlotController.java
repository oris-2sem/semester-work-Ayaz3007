package com.example.ludikgames.controller;

import com.example.ludikgames.dto.slots.*;
import com.example.ludikgames.model.User;
import com.example.ludikgames.service.SlotService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games/slots")
public class SlotController {
    private final SlotService slotService;
    private final UsersService usersService;

    @PostMapping("/{id}/spin")
    public ResponseEntity<SpinResultDto> getSuccessOfSpin(@PathVariable("id") String id, @RequestBody SpinDto spinDto, Principal principal) {
        Integer balance = usersService.setBalanceByEmail(principal.getName(), -spinDto.getStake());
        return ResponseEntity.ok(SpinResultDto.builder()
                .balance(balance)
                .result("Success")
                .build());
    }

    @PostMapping("/{id}/result")
    public ResponseEntity<WinResultDto> getWinResult(@RequestBody WinSpinDto spinDto, Principal principal, @PathVariable String id) {
        WinResultDto result = slotService.getResult(spinDto, principal.getName());
        Integer balance = usersService.setBalanceByEmail(principal.getName(), result.getWonMoney());

        result.setBalance(balance);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addSlot(@ModelAttribute("slot") SlotDto slotDto) {
        slotService.addSlot(slotDto);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/favourite")
    public ResponseEntity<?> addSlotToFavourite(@RequestBody FavouriteSlotDto favouriteSlotDto, Principal principal) {
        slotService.addSlotToFavourite(favouriteSlotDto, principal.getName());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/favourite")
    public ResponseEntity<?> deleteSlotFromFavourite(@RequestBody FavouriteSlotDto favouriteSlotDto, Principal principal) {
        slotService.deleteSlotFromFavourite(favouriteSlotDto, principal.getName());
        return ResponseEntity.accepted().build();
    }
}
