package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.slots.FavouriteSlotDto;
import com.example.ludikgames.dto.slots.SlotDto;
import com.example.ludikgames.dto.slots.WinResultDto;
import com.example.ludikgames.dto.slots.WinSpinDto;
import com.example.ludikgames.exceptions.NotFoundException;
import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.Slot;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.LeaderBoardRepository;
import com.example.ludikgames.repository.SlotRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.service.LeaderboardService;
import com.example.ludikgames.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final UsersRepository usersRepository;
    private final LeaderBoardRepository leaderBoardRepository;
    private static final double[] symbolsPrice = new double[]{0.25, 0.5, 1, 2, 5};
    @Override
    public WinResultDto getResult(WinSpinDto spinDto, String email) {
        double money = 0;
        List<Integer> symbols = spinDto.getSymbols();
        Integer stake = spinDto.getStake();
        List<Integer> wonSymbols = new ArrayList<>();
        money += getResultOfLine(new int[]{0, 3, 6, 9, 12}, symbols, stake, wonSymbols);
        money += getResultOfLine(new int[]{1, 4, 7, 10, 13}, symbols, stake, wonSymbols);
        money += getResultOfLine(new int[]{2, 5, 8, 11, 14}, symbols, stake, wonSymbols);
        money += getResultOfLine(new int[]{0, 4, 8, 11, 14}, symbols, stake, wonSymbols);
        money += getResultOfLine(new int[]{2, 4, 6, 9, 12}, symbols, stake, wonSymbols);

        User user = usersRepository.findByEmail(email).orElseThrow();
        Leaderboard leaderboard = user.getPlace();
        leaderboard.setPoints(leaderboard.getPoints() + (int)money/stake);
        user.setPlace(leaderboard);
        leaderboard.setUser(user);

        usersRepository.save(user);
        leaderBoardRepository.save(leaderboard);
        return WinResultDto.builder()
                .wonMoney((int) money)
                .wonSymbols(wonSymbols)
                .build();
    }

    @Override
    public boolean addSlot(SlotDto slotDto) {
        Slot slot = Slot.builder()
                .slotName(slotDto.getSlotName())
                .viewName(slotDto.getViewName())
                .build();
        slotRepository.save(slot);
        return true;
    }

    @Override
    public Slot getSlot(String id) {
        UUID uuid = UUID.fromString(id);
        Slot slot = slotRepository.getReferenceById(uuid);
        return slot;
    }

    @Override
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    @Override
    public void addSlotToFavourite(FavouriteSlotDto favouriteSlotDto, String email) {
        Slot slot = slotRepository.getReferenceById(UUID.fromString(favouriteSlotDto.getSlotId()));
        User user = usersRepository.findByEmail(email).orElseThrow();
        if(!user.getSlots().contains(slot)) {
            user.getSlots().add(slot);
            usersRepository.save(user);
        }
    }


    @Override
    public void deleteSlotFromFavourite(FavouriteSlotDto favouriteSlotDto, String email) {
        Slot slot = slotRepository.getReferenceById(UUID.fromString(favouriteSlotDto.getSlotId()));
        User user = usersRepository.findByEmail(email).orElseThrow();
        slot.getUsers().remove(user);
        user.getSlots().remove(slot);
        slotRepository.save(slot);
        usersRepository.save(user);
    }

    public static double getResultOfLine(int[] line, List<Integer> symbols, Integer stake, List<Integer> wonSymbols) {
        double money = 0;
        if(symbols.get(line[0]).equals(symbols.get(line[1])) && symbols.get(line[1]).equals(symbols.get(line[2]))) {
            wonSymbols.add(line[0]);
            wonSymbols.add(line[1]);
            wonSymbols.add(line[2]);
            money += symbolsPrice[symbols.get(line[0])]*3*stake;
            if(symbols.get(line[3]).equals(symbols.get(line[2]))) {
                money += symbolsPrice[symbols.get(line[0])]*stake;
                wonSymbols.add(line[3]);
                if(symbols.get(line[3]).equals(symbols.get(line[4]))) {
                    money += symbolsPrice[symbols.get(line[0])]*stake;
                    wonSymbols.add(line[4]);
                }
            }
        }
        return money;
    }
}

