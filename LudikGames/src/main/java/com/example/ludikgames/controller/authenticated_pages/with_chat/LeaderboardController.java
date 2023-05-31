package com.example.ludikgames.controller.authenticated_pages.with_chat;

import com.example.ludikgames.dto.PlaceDto;
import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.User;
import com.example.ludikgames.service.ChatService;
import com.example.ludikgames.service.LeaderboardService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public String getLeaderboardPage(Model model) {
        List<PlaceDto> placeDtoList = leaderboardService.getUsersList();
        model.addAttribute("places", placeDtoList);
        return "leaderboard";
    }
}
