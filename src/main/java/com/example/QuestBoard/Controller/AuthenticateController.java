package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Service.QuestService;
import com.example.QuestBoard.Service.SolutionService;
import com.example.QuestBoard.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AuthenticateController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestService questService;
    @Autowired
    private SolutionService solutionService;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerAdventurerForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    @PostMapping("/register/save")
    public String registerAdventurer(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        User existingEmail = userService.findUserByEmail(userDTO.getEmail());
        User existingUsername = userService.findUserByUsername(userDTO.getUsername());

        if (existingEmail != null && existingEmail.getEmail() != null && !existingEmail.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", "taken_email", "Email already in use!");
        }

        if (existingUsername != null && existingUsername.getUsername() != null && !existingUsername.getUsername().isEmpty()) {
            bindingResult.rejectValue("username", "taken_username", "Title already in use!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/register";
        }

        userService.saveUser(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users")
    public String seeUsers(Model model) {
        List<UserDTO> userDTOList = userService.findAllUsers();
        model.addAttribute("users", userDTOList);
        return "users";
    }

    @GetMapping("/users/remove-user/{id}")
    public String removeUserById(@PathVariable Long id, Model model) {
        userService.removeUserById(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/users";
    }

    @GetMapping("/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {

        if (authentication != null)
            return authentication.getName();
        else
            return "";
    }

    @GetMapping("/users/my-quests")
    public String viewMyQuests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //User user = userService.findUserByUsername(username);
        //List<Quest> quests = user.getUserQuests();
        List<QuestDTO> quests = questService.findAllQuests().stream()
                .filter(q -> Objects.equals(q.getAuthor(), username))
                .collect(Collectors.toList());
        model.addAttribute("quests", quests);
        return "my-quests";
    }

    @GetMapping("/users/add-quest")
    public String addQuest(Model model) {
        QuestDTO questDTO = new QuestDTO();
        model.addAttribute("quest", questDTO);
        return "add-quest";
    }

    @GetMapping("/quests")
    public String viewAllQuests(Model model) {
        List<QuestDTO> questDTOList = questService.findAllQuests();
        model.addAttribute("quests", questDTOList);
        return "quests";
    }

    @PostMapping("/users/save-quest")
    public String saveQuest(@Valid @ModelAttribute("quest") QuestDTO questDTO, BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("quest", questDTO);
            return "add-quest";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            Quest quest = questService.saveQuest(questDTO, user);
            userService.bindQuest(quest, user);
        }
        return "redirect:/quests";
    }

    @GetMapping("/quests/{id}")
    public String viewQuest(@PathVariable Long id, Model model) {
        QuestDTO questDTO = questService.findQuestById(id);
        model.addAttribute("quest", questDTO);
        return "quest-details";
    }

    @GetMapping("/quests/{id}/add-solution")
    public String addSolution(@PathVariable Long id, Model model) {
        SolutionDTO solutionDTO = new SolutionDTO();
        model.addAttribute("solution", solutionDTO);
        model.addAttribute("quest_id", id);
        return "add-solution";
    }

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
        //Quest quest = questService.findQuest(id);
        Quest quest = questService.getQuestReference(id);

        if (user != null && quest != null) {
            Solution solution = solutionService.saveSolution(solutionDTO, quest, user);
            quest.addSolution(solution);
        }

        return "redirect:/quests";
    }

    @GetMapping("/quests/{id}/solutions")
    public String viewSolutions(@PathVariable Long id, Model model) {
        List<SolutionDTO> solutionDTOList = solutionService.findAllSolutions().stream()
                .filter(s -> Objects.equals(s.getQuest(), id))
                .toList();
        model.addAttribute("solutions", solutionDTOList);
        return "solutions";
    }

    @GetMapping("/solutions")
    public String viewAllSolutions(Model model) {
        List<SolutionDTO> solutionDTOList = solutionService.findAllSolutions();
        model.addAttribute("solutions", solutionDTOList);
        return "test-solutions";
    }
}
