package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.Quest;
import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Service.QuestService;
import com.example.QuestBoard.Service.UserService;
import jakarta.validation.Valid;
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
import java.util.stream.Collectors;

@Controller
public class QuestController {
    @Autowired
    QuestService questService;
    @Autowired
    UserService userService;

    /**
     * Gets the HTML page for the /quests request. Shows all quests in a table.
     * @param model contains the list of all quests from the database, DTO format
     * @return the html page "quests"
     */
    @GetMapping("/quests")
    public String viewAllQuests(Model model) {
        List<QuestDTO> questDTOList = questService.findAllQuests();
        model.addAttribute("quests", questDTOList);
        return "quests";
    }

    /**
     * Gets the HTML page for the /quests/{id} request with variable id, representing a quest's id.
     * @param id represents the quest's id that will be viewed
     * @param model contains the questDTO with the above id
     * @return the html page "quest-details"
     */
    @GetMapping("/quests/{id}")
    public String viewQuest(@PathVariable Long id, Model model) {
        QuestDTO questDTO = questService.findQuestDTOById(id);
        model.addAttribute("quest", questDTO);
        return "quest-details";
    }

    /**
     * Gets the HTML page for the /quests/my-quests request. Shows all of the currently authenticated user's quests.
     * @param model contains the list of quests that the currently authenticated user has active
     * @return the html page "my-quests"
     */
    @GetMapping("/quests/my-quests")
    public String viewMyQuests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<QuestDTO> quests = questService.findAllQuests().stream()
                .filter(q -> Objects.equals(q.getAuthor(), username))
                .collect(Collectors.toList());
        model.addAttribute("quests", quests);
        return "my-quests";
    }

    /**
     * Gets the HTML page for the /quests/add-quest request. Allows for adding a new Quest.
     * @param model contains an empty QuestDTO
     * @return the html page "add-quest"
     */
    @GetMapping("/quests/add-quest")
    public String addQuest(Model model) {
        QuestDTO questDTO = new QuestDTO();
        model.addAttribute("quest", questDTO);
        return "add-quest";
    }

    /**
     * Gets the HTML page for the /quests/save-quest PostRequest. Saves the questDTO to the database.
     * @param questDTO the quest to be saved
     * @param bindingResult checker in case the binding fails
     * @param model contains the questDTO
     * @return redirects to /quests in case of success, returns html "add-quest" in case of failure
     */
    @PostMapping("/quests/save-quest")
    public String saveQuest(@Valid @ModelAttribute("quest") QuestDTO questDTO, BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("quest", questDTO);
            return "add-quest";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null && user.getTokens() >= questDTO.getReward()) {
            userService.takeReward(username, questDTO.getReward());
            questService.saveQuest(questDTO, user);
        }
        return "redirect:/quests";
    }

    /**
     * Gets the HTML page for the /quests/remove-quest/{id} request with variable id. Allows for removing a certain quest.
     * @param id the id of the quest to be removed
     * @param model contains the new and updated list of quests
     * @return redirects back to the /quests/my-quests request
     */
    @GetMapping("/quests/remove-quest/{id}")
    public String removeQuest(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        Quest quest = questService.findQuestById(id);
        userService.giveReward(user, quest.getReward());
        questService.removeQuestById(id);
        model.addAttribute("quests", questService.findAllQuests());
        return "redirect:/quests/my-quests";
    }
}
