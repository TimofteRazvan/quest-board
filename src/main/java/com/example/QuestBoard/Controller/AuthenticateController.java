package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;
import com.example.QuestBoard.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthenticateController {
    @Autowired
    private UserService userService;

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
}
