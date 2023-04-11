package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestService implements QuestServiceInterface {
    @Autowired
    QuestRepository questRepository;

    @Override
    public QuestDTO findQuestDTOById(Long id) {
        Quest quest = questRepository.findById(id).orElseThrow(() -> new RuntimeException("Quest not found!"));
        String username = null;
        if (quest.getUser() != null) {
            username = quest.getUser().getUsername();
        }
        return new QuestDTO(quest.getId(), quest.getTitle(), quest.getDescription(), quest.getReward(), username);
    }

    @Override
    public Quest findQuestById(Long id) {
        return questRepository.findById(id).orElseThrow(() -> new RuntimeException("Quest not found!"));
    }

    @Override
    public Quest getQuestReference(Long id) {
        return questRepository.getById(id);
    }

    @Override
    public List<QuestDTO> findAllQuests() {
        List<Quest> quests = questRepository.findAll();
        return quests.stream()
                .map(this::mapQuestToQuestDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Quest to a QuestDTO, getting rid of unnecessary data for displaying.
     * @param quest the quest which will be mapped to a DTO
     * @return the DTO created from the Quest object
     */
    private QuestDTO mapQuestToQuestDTO(Quest quest) {
        QuestDTO questDTO = new QuestDTO();
        questDTO.setId(quest.getId());
        questDTO.setTitle(quest.getTitle());
        questDTO.setDescription(quest.getDescription());
        questDTO.setReward(quest.getReward());
        if (quest.getUser() != null) {
            questDTO.setAuthor(quest.getUser().getUsername());
        }
        return questDTO;
    }

    @Override
    public Quest saveQuest(QuestDTO questDTO, User user) {
        Quest quest = new Quest();
        quest.setTitle(questDTO.getTitle());
        quest.setDescription(questDTO.getDescription());
        quest.setReward(questDTO.getReward());
        quest.setUser(user);
        questRepository.save(quest);
        return quest;
    }

    @Override
    public void removeQuestById(Long id) {
        Quest quest = questRepository.findById(id).orElseThrow(() -> new RuntimeException("Quest not found!"));
        questRepository.deleteById(quest.getId());
    }

    @Override
    public void removeQuestsOfUser(Long id) {
        List<Quest> quests = questRepository.findAll();
        for (Quest quest : quests) {
            if (quest.getUser() != null && Objects.equals(quest.getUser().getId(), id)) {
                questRepository.deleteById(quest.getId());
            }
        }
    }
}
