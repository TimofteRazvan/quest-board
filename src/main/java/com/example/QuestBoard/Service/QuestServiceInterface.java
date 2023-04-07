package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;

import java.util.List;

public interface QuestServiceInterface {
    List<QuestDTO> findAllQuests();

    void saveQuest(QuestDTO questDTO, User user);
}
