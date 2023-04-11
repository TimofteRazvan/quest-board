package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;

import java.util.List;

public interface UserServiceInterface {
    /**
     * Get all the User entities from the UserRepository converted into DTOs.
     * @return the list of users from the database, converted to DTOs
     */
    List<UserDTO> findAllUsers();

    /**
     * Get all User entities from the UserRepository converted into DTOs, ordered ASCENDING by amount of tokens
     * @return the list of users, converted to DTOs and sorted ascending
     */
    List<UserDTO> findAllUsersByTokensAscending();

    /**
     * Get all User entities from the UserRepository converted into DTOs, ordered ASCENDING by amount of tokens
     * @return the list of users, converted to DTOs and sorted descending
     */
    List<UserDTO> findAllUsersByTokensDescending();

    /**
     * Get User object from database if it matches the id argument given.
     * @param id the id by which to search the user
     * @return the user if found, else throws a RunTimeException
     */
    User findUserById(Long id);

    /**
     * Get User entity from database if it matches the email argument given.
     * @param email the email by which to search the user
     * @return the user if found, else will return null
     */
    User findUserByEmail(String email);

    /**
     * Get User entity from database if it matches the username argument given
     * @param username the username by which to search the user
     * @return the user if found, else will return null
     */
    User findUserByUsername(String username);

    /**
     * Creates a new User entity and sets its values according to the DTO, then persists it.
     * @param userDTO the UserDTO which will be saved as a User into the database
     */
    void saveUser(UserDTO userDTO);

    /**
     * Removes a User from the database if it matches the id argument given
     * @param id the id by which to search and remove the user
     */
    void removeUserById(Long id);

    /**
     * Adds tokens to a User entity and saves the changes. Checks for badges.
     * @param user the user who will receive the tokens
     * @param tokens the tokens to be given to the user
     */
    void giveReward(User user, int tokens);

    /**
     * Takes tokens from a User entity and saves the changes. Checks for badges.
     * @param username the username of the user who will lose the tokens
     * @param tokens the tokens to be taken from the user
     */
    void takeReward(String username, int tokens);
}
