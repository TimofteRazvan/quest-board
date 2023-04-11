package com.example.QuestBoard.Controller;

import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;
import com.example.QuestBoard.Service.QuestService;
import com.example.QuestBoard.Service.SolutionService;
import com.example.QuestBoard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SolutionService solutionService;
    @Autowired
    QuestService questService;

    /**
     * Gets the HTML page for the /users request. Allows for viewing all the users in the database.
     * @param model contains a list of userDTOs
     * @return the html page "users"
     */
    @GetMapping("/users")
    public String seeUsers(Model model) {
        List<UserDTO> userDTOList = userService.findAllUsers();
        model.addAttribute("users", userDTOList);
        return "users";
    }

    /**
     * Gets the HTML page for the /user request. Allows to view currently logged-in user's details.
     * @param model contains the currently logged-in user
     * @return the html page "user-details"
     */
    @GetMapping("/user")
    public String seeCurrentUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "user-details";
    }

    /**
     * Gets the HTML page for the /users/user-admin-details/{id} request, with variable id. Allows admins to view
     * any user and admin-only details, such as their roles.
     * @param id the id of the user to be viewed
     * @param model contains the user to be viewed
     * @return the html page "user-admin-details"
     */
    @GetMapping("/users/user-admin-details/{id}")
    public String viewUserDetails(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user-admin-details";
    }

    /**
     * Gets the HTML page for the /users/remove-user/{id} request with variable id. Available only for id to delete
     * the user with the aforementioned variable id.
     * @param id the id of the user to be deleted
     * @param model contains the new and updated list of users
     * @return redirects to the /users request
     */
    @GetMapping("/users/remove-user/{id}")
    public String removeUser(@PathVariable Long id, Model model) {
        solutionService.removeSolutionsOfUser(id);
        questService.removeQuestsOfUser(id);
        userService.removeUserById(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/users";
    }

    /**
     * Gets the HTML page for the /leaderboard request. Allows to view all users in the database sorted by their tokens
     * in descending fashion based on their token count, with rankings.
     * @param model contains the list of user DTOs, sorted
     * @return the html page "leaderboard"
     */
    @GetMapping("/leaderboard")
    public String viewLeaderboard(Model model) {
        List<UserDTO> userDTOList = userService.findAllUsersByTokensDescending();
        model.addAttribute("users", userDTOList);
        return "leaderboard";
    }
}
