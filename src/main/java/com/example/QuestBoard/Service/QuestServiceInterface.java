package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;

import java.util.List;

public interface QuestServiceInterface {
    /**
     * Get all the Quest entities from the QuestRepository converted into DTOs.
     * @return the list of quests from the database, converted to DTOs
     */
    List<QuestDTO> findAllQuests();

    /**
     * Get Quest object from database if it matches the id argument given.
     * @param id the id by which to search the quest
     * @return the Quest object if found, else throws a RunTimeException
     */
    Quest findQuestById(Long id);

    /**
     * Get QuestDTO object from database if it matches the id argument given.
     * @param id the id by which to search the quest
     * @return the QuestDTO object if found, else throws a RunTimeException
     */
    QuestDTO findQuestDTOById(Long id);

    /**
     * Makes use of JPA's getById(id) function to get a reference to the Quest with the given id argument
     * @param id the id of the quest we get a reference of
     * @return the reference of the quest
     */
    Quest getQuestReference(Long id);

    /**
     * Creates a new Quest object and sets its values according to the DTO, sets the User, then persists it.
     * @param questDTO the QuestDTO object which will be saved as a Quest into the database
     * @param user the user who has written and saved the quest
     */
    Quest saveQuest(QuestDTO questDTO, User user);

    /**
     * Removes a Quest from the database if it matches the id argument given
     * @param id the id by which to search and remove the quest
     */
    void removeQuestById(Long id);

    /**
     * Removes all quests whose user ID match with the given ID
     * @param id the ID of the user whose quests are removed
     */
    void removeQuestsOfUser(Long id);
}
