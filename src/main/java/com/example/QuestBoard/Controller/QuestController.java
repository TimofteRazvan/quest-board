package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.QuestDTO;
import com.example.QuestBoard.Entity.UserDTO;
import com.example.QuestBoard.Service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class QuestController {
    @Autowired
    QuestService questService;

    @GetMapping("/quests")
    public String viewAllQuests(Model model) {
        List<QuestDTO> questDTOList = questService.findAllQuests();
        model.addAttribute("quests", questDTOList);
        return "quests";
    }

    @GetMapping("/my-quests")
    public String viewMyQuests(@ModelAttribute("user") UserDTO userDTO, Model model) {
        //List<QuestDTO> questDTOList = userDTO.get
        return "my-quests";
    }
}
