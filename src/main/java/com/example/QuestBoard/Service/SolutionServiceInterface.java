package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.Solution;
import com.example.QuestBoard.Entity.SolutionDTO;
import com.example.QuestBoard.Entity.User;

import java.util.List;

public interface SolutionServiceInterface {
    /**
     * Get all the Solution entities from the SolutionRepository converted into DTOs.
     * @return the list of solutions from the database, converted to DTOs
     */
    List<SolutionDTO> findAllSolutions();

    /**
     * Get Solution entity from database if it matches the id argument given.
     * @param id the id by which to search the solution
     * @return the solution if found, else throws a RunTimeException
     */
    Solution findSolutionById(Long id);

    /**
     * Creates a new Solution entity and sets its values according to the DTO, sets Quest and User, then persists it.
     * @param solutionDTO the SolutionDTO which will be saved into the database as Solution
     * @param quest the quest for which the solution has been written
     * @param user the user who wrote the solution
     * @return the Solution object saved into the database
     */
    Solution saveSolution(SolutionDTO solutionDTO, Quest quest, User user);

    /**
     * Removes a Solution entity from the database if it matches the id argument given
     * @param id the id by which to search and remove the solution
     */
    void removeSolutionById(Long id);

    /**
     * Removes all solutions whose user ID match with the given ID
     * @param id the ID of the user whose solutions are removed
     */
    void removeSolutionsOfUser(Long id);
}
