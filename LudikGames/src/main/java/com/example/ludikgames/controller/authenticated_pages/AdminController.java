package com.example.ludikgames.controller.authenticated_pages;

import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UsersService usersService;
    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", usersService.getAllUsers());
        return "admin";
    }


}
