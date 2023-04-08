package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;

import java.util.List;

public interface QuestServiceInterface {
    List<QuestDTO> findAllQuests();

    Quest saveQuest(QuestDTO questDTO, User user);
}
