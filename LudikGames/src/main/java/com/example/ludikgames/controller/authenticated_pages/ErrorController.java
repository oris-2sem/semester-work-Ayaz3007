package com.example.ludikgames.controller.authenticated_pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = Integer.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        model.addAttribute("code", statusCode);
        String message = "";
        if(statusCode == 404) {
            message = "Страница не найдена";
        }
        if(statusCode == 500) {
            message = "Ошибка на нашей стороне";
        }
        if(statusCode == 403) {
            message = "В доступе отказано";
        }
        model.addAttribute("message", message);

        return "error";
    }
}
