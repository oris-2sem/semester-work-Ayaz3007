package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.PlaceDto;
import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.LeaderBoardRepository;
import com.example.ludikgames.repository.LeaderboardCustomRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.repository.impl.LeaderboardRepositoryImpl;
import com.example.ludikgames.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderboardService {
    private final LeaderBoardRepository leaderBoardRepository;
    private final LeaderboardCustomRepository leaderboardCustomRepository;
    private final UsersRepository usersRepository;

    @Override
    public void addUser(User user) {
        Leaderboard leaderboard = Leaderboard.builder()
                .user(user)
                .points(0)
                .build();

        user.setPlace(leaderboard);
        leaderBoardRepository.save(leaderboard);
        usersRepository.save(user);
    }

    @Override
    public List<PlaceDto> getUsersList() {
        List<Leaderboard> places = leaderboardCustomRepository.findAllByNotBannedUsers();
        List<PlaceDto> placeDtoList = new ArrayList<>();
        for(Leaderboard leaderboard : places) {
            placeDtoList.add(PlaceDto.builder()
                            .nickname(leaderboard.getUser().getNickname())
                            .points(leaderboard.getPoints())
                    .build());
        }
        return placeDtoList;
    }
}
