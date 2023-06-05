package com.example.ludikgames.controller;

import com.example.ludikgames.service.UsersService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final UsersService usersService;
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Principal principal) {

        boolean isAuthenticated = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("nickname", usersService.getNicknameByEmail(principal.getName()));
            model.addAttribute("balance", usersService.getBalanceByEmail(principal.getName()));
            isAuthenticated = true;
        }
        int statusCode;
        model.addAttribute("isAuthenticated", isAuthenticated);
        try {
            statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        } catch (Exception e) {
            statusCode = 500;
        }

        model.addAttribute("code", statusCode);
        String message = "";
        if(statusCode == 404) {
            message = "Страница не найдена";
        }
        if(statusCode == 403) {
            message = "В доступе отказано";
        } else {
            message = "Ошибка на нашей стороне";
        }
        model.addAttribute("message", message);

        return "error";
    }
}
