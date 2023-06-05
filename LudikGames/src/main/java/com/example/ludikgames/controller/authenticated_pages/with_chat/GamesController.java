package com.example.ludikgames.controller.authenticated_pages.with_chat;

import com.example.ludikgames.model.Slot;
import com.example.ludikgames.service.SlotService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(path = "/games")
@RequiredArgsConstructor
public class GamesController {
    private final SlotService slotService;
    private final UsersService usersService;

    @GetMapping("/slots")
    public String getSlotsPage(Model model, Principal principal) {
        model.addAttribute("slots", slotService.getAllSlots());
        model.addAttribute("user", usersService.getUserByEmail(principal.getName()));
        return "slots";
    }

    @GetMapping("/slots/{id}")
    public String getSlotsById(@PathVariable("id") String slotId, Model model){
        Slot slot = slotService.getSlot(slotId);
        model.addAttribute("slotName", slot.getSlotName().toLowerCase());
        model.addAttribute("viewName", slot.getViewName());
        return "slot";
    }

    @GetMapping("/favourites")
    public String getFavouritesSlotsPage(Model model, Principal principal) {
        model.addAttribute("slots", usersService.getFavouriteSlots(principal.getName()));
        return "favourites";
    }
}
