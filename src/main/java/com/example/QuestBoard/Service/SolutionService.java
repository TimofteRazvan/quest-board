package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.Solution;
import com.example.QuestBoard.Entity.SolutionDTO;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolutionService implements SolutionServiceInterface {
    @Autowired
    SolutionRepository solutionRepository;

    @Override
    public List<SolutionDTO> findAllSolutions() {
        List<Solution> solutions = solutionRepository.findAll();
        return solutions.stream()
                .map(this::mapSolutionToSolutionDTO)
                .collect(Collectors.toList());
    }

    public Solution findSolutionById(Long id) {
        return solutionRepository.findById(id).orElseThrow(() -> new RuntimeException("No such solution!"));
    }

    private SolutionDTO mapSolutionToSolutionDTO(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setId(solution.getId());
        solutionDTO.setEntry(solution.getEntry());
        if (solution.getQuest() != null) {
            solutionDTO.setQuest(solution.getQuest().getId());
        }
        return solutionDTO;
    }

    @Override
    public Solution saveSolution(SolutionDTO solutionDTO, Quest quest, User user) {
        Solution solution = new Solution(user, quest, solutionDTO.getEntry());
        solutionRepository.save(solution);
        return solution;
    }
}
