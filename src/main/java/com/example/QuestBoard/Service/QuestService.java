package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestService implements QuestServiceInterface {
    @Autowired
    QuestRepository questRepository;

    @Override
    public List<QuestDTO> findAllQuests() {
        List<Quest> quests = questRepository.findAll();
        return quests.stream()
                .map(this::mapQuestToQuestDTO)
                .collect(Collectors.toList());
    }

    private QuestDTO mapQuestToQuestDTO(Quest quest) {
        QuestDTO questDTO = new QuestDTO();
        questDTO.setTitle(quest.getTitle());
        questDTO.setDescription(quest.getDescription());
        questDTO.setReward(quest.getReward());
        return questDTO;
    }

    @Override
    public void saveQuest(QuestDTO questDTO, User user) {
        Quest quest = new Quest();
        quest.setTitle(questDTO.getTitle());
        quest.setDescription(quest.getDescription());
        quest.setReward(quest.getReward());
        quest.setUser(user);
        questRepository.save(quest);
    }
}
