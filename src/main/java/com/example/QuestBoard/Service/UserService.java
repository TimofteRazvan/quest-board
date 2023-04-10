package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Repository.RoleRepository;
import com.example.QuestBoard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
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
        if (user.getUsername().contains("admin1906")) {
            role = roleRepository.findByName("ROLE_ADMIN");
        }
        else {
            role = roleRepository.findByName("ROLE_USER");
        }
        if (role == null) {
            role = checkRole();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    public void bindQuest(Quest quest, User user) {
        List<Quest> quests = user.getUserQuests();
        quests.add(quest);
        user.setUserQuests(quests);
    }

    private Role checkRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public void removeUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.deleteById(user.getId());
    }

    //TODO: Fix this
    @Override
    public void removeUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.deleteById(user.getId());
        }
    }

    @Override
    public void removeUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.deleteById(user.getId());
        }
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
}
