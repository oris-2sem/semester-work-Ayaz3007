package com.example.ludikgames.controller;

import com.example.ludikgames.dto.BalanceDto;
import com.example.ludikgames.dto.EmailDto;
import com.example.ludikgames.dto.PhoneDto;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public String getMainPage() {
        return "users";
    }

    @PostMapping("/phone")
    public String updatePhoneNumber(@ModelAttribute("phone") PhoneDto phone, Principal principal) {
        usersService.updatePhoneNumber(phone.getPhoneNumber(), principal.getName());
        return "redirect:/profile";
    }

    @PostMapping("/balance")
    public String deposit(@ModelAttribute("balance") BalanceDto balanceDto, Principal principal) {
        usersService.setBalanceByEmail(principal.getName(), balanceDto.getBalance());
        return "redirect:/deposit";
    }

    @PostMapping("/change-role")
    public String changeRole(@ModelAttribute("roleChanging")EmailDto emailDto) {
        usersService.changeUserRoleByEmail(emailDto.getEmail());
        return "redirect:/admin";
    }

    @PostMapping("/change-state")
    public String changeState(@ModelAttribute("stateChanging")EmailDto emailDto, Principal principal) {
        usersService.changeUserStateByEmail(emailDto.getEmail());
        if(emailDto.getEmail().equals(principal.getName())) {
            return "redirect:/logout";
        }
        return "redirect:/admin";
    }
}
