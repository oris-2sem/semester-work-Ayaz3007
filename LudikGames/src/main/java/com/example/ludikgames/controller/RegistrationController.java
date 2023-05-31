package com.example.ludikgames.controller;

import com.example.ludikgames.dto.SignUpDto;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UsersService usersService;

    @GetMapping
    public String getRegistrationPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/games/slots";
        }
        model.addAttribute("isAuthenticated", false);
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("reg_data") SignUpDto signUpData, Model model) {
        boolean isRegistrationSuccessful = usersService.signUp(signUpData);

        if(!isRegistrationSuccessful) {
            model.addAttribute("error", "Пользователь с таким E-mail уже существует");
            return "registration";
        }

        return "redirect:/login";
    }
}
