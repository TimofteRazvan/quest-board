package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.BadgeDTO;
import com.example.QuestBoard.Service.BadgeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BadgeController {
    @Autowired
    BadgeService badgeService;

    /**
     * Returns the html for /badges request. Adds list of badges from database to model.
     * @param model contains list of badges
     * @return the "badges" html for the request
     */
    @GetMapping("/badges")
    public String viewBadges(Model model) {
        List<BadgeDTO> badgeDTOList = badgeService.findAllBadges();
        model.addAttribute("badges", badgeDTOList);
        return "badges";
    }

    /**
     * Returns the html for /badges/add-badge request. Adds empty BadgeDTO to it to be filled.
     * @param model contains an empty BadgeDTO
     * @return the "add-badge" html for the request
     */
    @GetMapping("/badges/add-badge")
    public String addBadge(Model model) {
        BadgeDTO badgeDTO = new BadgeDTO();
        model.addAttribute("badge", badgeDTO);
        return "add-badge";
    }

    /**
     * Saves the previously empty BadgeDTO.
     * @param badgeDTO the previously empty BadgeDTO that will now be saved
     * @param bindingResult checker in case the binding fails
     * @param model contains the BadgeDTO
     * @return the "add-badge" html if failed to save, redirects to /badges otherwise
     */
    @PostMapping("/badges/save-badge")
    public String saveBadge(@Valid @ModelAttribute("badge") BadgeDTO badgeDTO, BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("badge", badgeDTO);
            return "add-badge";
        }
        badgeService.saveBadge(badgeDTO);
        return "redirect:/badges";
    }

    /**
     * Removes a badge by PathVariable id mentioned in the path.
     * @param id taken from the request's path, removes badge with aforementioned id
     * @param model contains the new and updated list of badges
     * @return redirects to the /badges request
     */
    @GetMapping("/badges/remove-badge/{id}")
    public String removeBadge(@PathVariable Long id, Model model) {
        badgeService.removeBadgeById(id);
        model.addAttribute("badges", badgeService.findAllBadges());
        return "redirect:/badges";
    }
}
