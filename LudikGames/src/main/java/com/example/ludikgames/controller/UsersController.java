package com.example.ludikgames.controller;

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

    @PostMapping
    public String updatePhoneNumber(@ModelAttribute("phone") PhoneDto phone, Principal principal) {
        usersService.updatePhoneNumber(phone.getPhoneNumber(), principal.getName());
        return "redirect:/profile";
    }
}
