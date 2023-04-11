package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticateController {
    @Autowired
    private UserService userService;

    /**
     * Gets the html for the /index request
     * @return the html for index
     */
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    /**
     * Returns the register html when accessing /register. Adds an empty UserDTO to the model.
     * @param model contains an empty UserDTO to be filled
     * @return the html for the page
     */
    @GetMapping("/register")
    public String registerAdventurerForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    /**
     * Registers a User. Checks if the email already exists, same for username. If yes, redirects to register page.
     * @param userDTO the user model from the html
     * @param bindingResult the checker in case there have been errors with binding the model
     * @param model the model that is used in the html file
     * @return success message if correct, otherwise redirects back to /register
     */
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

    /**
     * Gets the html for the /login request.
     * @return the html for the page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
