package com.example.ludikgames.controller;

import com.example.ludikgames.dto.SignUpDto;
import com.example.ludikgames.service.RegistrationService;
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
    private final RegistrationService registrationService;

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
//        if(!registrationService.isEmailValid(signUpData.getEmail())) {
//            model.addAttribute("error", "Invalid email");
//            model.addAttribute("isAuthenticated", false);
//            return "registration";
//        }

        boolean isRegistrationSuccessful = usersService.signUp(signUpData);

        if(!isRegistrationSuccessful ) {
            model.addAttribute("error", "Пользователь с таким E-mail уже существует");
            model.addAttribute("isAuthenticated", false);
            return "registration";
        }


        return "redirect:/login";
    }
}
