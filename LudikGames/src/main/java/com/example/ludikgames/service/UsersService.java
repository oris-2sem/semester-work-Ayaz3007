package com.example.ludikgames.service;

import com.example.ludikgames.dto.SignUpDto;
import com.example.ludikgames.model.Slot;
import com.example.ludikgames.model.User;

import java.util.List;
import java.util.Set;

public interface UsersService {
    boolean signUp(SignUpDto signUpDto);

    Integer getBalanceByEmail(String email);

    Integer setBalanceByEmail(String email, Integer money);

    String getNicknameByEmail(String email);

    Integer getRoleByEmail(String email);

    User getModeratorWithMinimumChats();

    User getModeratorByEmail(String email);

    Set<Slot> getFavouriteSlots(String email);

    void updatePhoneNumber(String phoneNumber, String email);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    void changeUserRoleByEmail(String email);
    void changeUserStateByEmail(String email);

}
