package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.Solution;
import com.example.QuestBoard.Entity.SolutionDTO;
import com.example.QuestBoard.Entity.User;

import java.util.List;

public interface SolutionServiceInterface {
    List<SolutionDTO> findAllSolutions();
    Solution saveSolution(SolutionDTO solutionDTO, Quest quest, User user);
}
