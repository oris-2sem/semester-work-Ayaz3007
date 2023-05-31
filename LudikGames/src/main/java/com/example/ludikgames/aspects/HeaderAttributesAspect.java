package com.example.ludikgames.aspects;

import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice("com.example.ludikgames.controller.authenticated_pages")
@RequiredArgsConstructor
public class HeaderAttributesAspect {

    private final UsersService usersService;

    @ModelAttribute
    public void addNicknameAndBalanceModels(Model model, Principal principal) {
        model.addAttribute("balance", usersService.getBalanceByEmail(principal.getName()));
        model.addAttribute("nickname", usersService.getNicknameByEmail(principal.getName()));
        model.addAttribute("role", usersService.getRoleByEmail(principal.getName()));
        model.addAttribute("isAuthenticated", true);
    }
}
