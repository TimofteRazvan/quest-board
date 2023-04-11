package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Repository.RoleRepository;
import com.example.QuestBoard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user!"));
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAllUsersByTokensAscending() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .sorted(Comparator.comparingInt(UserDTO::getTokens))
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAllUsersByTokensDescending() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .sorted(Comparator.comparingInt(UserDTO::getTokens).reversed())
                .collect(Collectors.toList());
    }

    private UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setTokens(user.getTokens());
        return userDTO;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setTokens(100);

        Role role = new Role();
        String role_name;
        if (userDTO.getEmail().contains("admin@gmail.com")) {
            role_name = "ROLE_ADMIN";
            role = roleRepository.findByName(role_name);
        }
        else {
            role_name = "ROLE_USER";
            role = roleRepository.findByName(role_name);
        }
        if (role == null) {
            role = checkRole(role_name);
        }
        user.setRoles(List.of(role));

        userRepository.save(user);
    }

    /*
    public void bindQuest(Quest quest, User user) {
        List<Quest> quests = user.getUserQuests();
        quests.add(quest);
        user.setUserQuests(quests);
    }
     */

    private Role checkRole(String role_name) {
        Role role = new Role(role_name);
        return roleRepository.save(role);
    }

    @Override
    public void removeUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.deleteById(user.getId());
    }

    public List<Quest> findQuestsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getUserQuests();
        }
        return null;
    }

    public void giveReward(User user, int tokens) {
        user.setTokens(user.getTokens() + tokens);
        userRepository.save(user);
    }

    public void takeReward(String username, int tokens) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setTokens(user.getTokens() - tokens);
            userRepository.save(user);
        }
    }
}
