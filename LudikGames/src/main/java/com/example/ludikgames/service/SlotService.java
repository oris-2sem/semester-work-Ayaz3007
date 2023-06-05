package com.example.ludikgames.service;

import com.example.ludikgames.dto.slots.FavouriteSlotDto;
import com.example.ludikgames.dto.slots.SlotDto;
import com.example.ludikgames.dto.slots.WinResultDto;
import com.example.ludikgames.dto.slots.WinSpinDto;
import com.example.ludikgames.model.Slot;

import java.security.Principal;
import java.util.List;

public interface SlotService {
    WinResultDto getResult(WinSpinDto spinDto, String email);
    boolean addSlot(SlotDto slotDto);
    Slot getSlot(String id);
    List<Slot> getAllSlots();
    void addSlotToFavourite(FavouriteSlotDto favouriteSlotDto, String email);

    void deleteSlotFromFavourite(FavouriteSlotDto favouriteSlotDto, String email);
}
