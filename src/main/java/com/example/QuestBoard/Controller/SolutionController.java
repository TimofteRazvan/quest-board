package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Service.QuestService;
import com.example.QuestBoard.Service.SolutionService;
import com.example.QuestBoard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class SolutionController {
    @Autowired
    SolutionService solutionService;
    @Autowired
    QuestService questService;
    @Autowired
    UserService userService;

    /**
     * Gets the HTML page for the /solutions request. Allows viewing all the solutions in the database.
     * @param model contains a list of solution DTOs from the database
     * @return the html page "solutions"
     */
    @GetMapping("/solutions")
    public String viewAllSolutions(Model model) {
        List<SolutionDTO> solutionDTOList = solutionService.findAllSolutions();
        model.addAttribute("solutions", solutionDTOList);
        return "solutions";
    }

    /**
     * Gets the HTML page for the /quests/add-solution request. Checks that user doesn't answer own question.
     * @param id the id of the quest to add a solution to
     * @param model contains the empty solutionDTO to be filled in and the quest's id
     * @return the html page "add-solution" if the user is not also the quest's author
     */
    @GetMapping("/quests/add-solution/{id}")
    public String addSolution(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        QuestDTO questDTO = questService.findQuestDTOById(id);
        if (Objects.equals(username, questDTO.getAuthor())) {
            return "redirect:/quests";
        }
        SolutionDTO solutionDTO = new SolutionDTO();
        model.addAttribute("solution", solutionDTO);
        model.addAttribute("quest_id", id);
        return "add-solution";
    }

    /**
     * Gets the HTML page for the /solutions/{id} request, with variable id representing the quest to be solved. Allows
     * for saving a solution to a quest, with the currently logged-in user as its solver.
     * @param solutionDTO the solutionDTO to be saved
     * @param id the quest's id that the solution is for
     * @param bindingResult checker in case the binding fails
     * @param model contains the solutionDTO to be saved
     * @return redirects to /solutions request in case of success, returns html page "add-solution" otherwise
     */
    @PostMapping("/solutions/{id}")
    public String saveSolution(@ModelAttribute("solution") SolutionDTO solutionDTO, @PathVariable Long id,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("solution", solutionDTO);
            return "add-solution";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Quest quest = questService.getQuestReference(id);

        if (user != null && quest != null) {
            Solution solution = solutionService.saveSolution(solutionDTO, quest, user);
            quest.addSolution(solution);
        }

        return "redirect:/quests";
    }

    /**
     * Gets the HTML page for the /quests/solutions/{id} request with variable id representing the id of the quest we
     * are viewing the solutions of.
     * @param id the id of the quest we want to see solutions of
     * @param model contains the list of all solutions attached to the quest
     * @return the html page "quest-solutions"
     */
    @GetMapping("/quests/solutions/{id}")
    public String viewSolutions(@PathVariable Long id, Model model) {
        List<SolutionDTO> solutionDTOList = solutionService.findAllSolutions().stream()
                .filter(s -> Objects.equals(s.getQuest(), id))
                .toList();
        model.addAttribute("solutions", solutionDTOList);
        return "quest-solutions";
    }

    /**
     * Gets the HTML page for the /quests/accept-solution/{id} with variable id representing the solution to be accepted.
     * Allows for accepting a solution and rewarding its author with tokens, after which the solution and quest are deleted.
     * @param id the id of the solution that is considered correct
     * @return redirects to /quests/my-quests request
     */
    @GetMapping("/quests/accept-solution/{id}")
    public String acceptSolution(@PathVariable Long id) {
        Solution solution = solutionService.findSolutionById(id);
        Quest quest = solution.getQuest();
        userService.giveReward(solution.getUser(), quest.getReward());
        solutionService.removeSolutionById(id);
        questService.removeQuestById(quest.getId());
        return "redirect:/quests/my-quests";
    }
}
