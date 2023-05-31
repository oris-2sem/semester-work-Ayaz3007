package com.example.ludikgames.controller;

import com.example.ludikgames.model.User;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainPageController {

    private final UsersService usersService;

    @GetMapping
    public String getMainPage(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = false;

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("nickname", usersService.getNicknameByEmail(principal.getName()));
            model.addAttribute("balance", usersService.getBalanceByEmail(principal.getName()));
            isAuthenticated = true;
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
        return "main";
    }

    @PostMapping
    public String getToLoginOrSlotsPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/games/slots";
        }
        return "redirect:/login";
    }
}
