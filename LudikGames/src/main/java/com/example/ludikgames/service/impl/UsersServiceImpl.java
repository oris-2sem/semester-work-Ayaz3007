package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.SignUpDto;
import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.Slot;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.UserCustomRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.service.LeaderboardService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserCustomRepository userCustomRepository;
    private final LeaderboardService leaderboardService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(SignUpDto signUpDto) {
        User userFromDB = usersRepository.findByEmail(signUpDto.getEmail()).orElse(null);

        if(userFromDB != null) {
            return false;
        }
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .nickname(signUpDto.getNickname())
                .email(signUpDto.getEmail())
                .balance(0)
                .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .state(User.State.CONFIRMED)
                .role(User.Role.USER)
                .build();

        leaderboardService.addUser(user);
        usersRepository.save(user);

        return true;
    }

    @Override
    public Integer getBalanceByEmail(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();

        return user.getBalance();
    }

    @Override
    public Integer setBalanceByEmail(String email, Integer money) {
        User user = usersRepository.findByEmail(email).orElseThrow();

        user.setBalance(user.getBalance() + money);
        usersRepository.save(user);

        return user.getBalance();
    }

    @Override
    public String getNicknameByEmail(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        return user.getNickname();
    }

    @Override
    public Integer getRoleByEmail(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        return user.getRole().ordinal();
    }

    @Override
    public User getModeratorWithMinimumChats() {
        return userCustomRepository.findModeratorsByMinimumCountOfChats().get(0);
    }

    @Override
    public User getModeratorByEmail(String email) {
        User moderator = usersRepository.findByEmail(email).orElseThrow();
        return moderator;
    }

    @Override
    public Set<Slot> getFavouriteSlots(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        return user.getSlots();
    }

    @Override
    public void updatePhoneNumber(String phoneNumber, String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        user.setPhone_number(phoneNumber);
        usersRepository.save(user);
    }
}
