package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;

import java.util.List;

public interface UserServiceInterface {
    List<UserDTO> findAllUsers();
    User findUserByEmail(String email);
    User findUserByUsername(String username);
    void saveUser(UserDTO userDTO);
}
