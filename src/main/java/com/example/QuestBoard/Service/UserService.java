package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.*;
import com.example.QuestBoard.Repository.BadgeRepository;
import com.example.QuestBoard.Repository.RoleRepository;
import com.example.QuestBoard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsersByTokensAscending() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .sorted(Comparator.comparingInt(UserDTO::getTokens))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsersByTokensDescending() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserDTO)
                .sorted(Comparator.comparingInt(UserDTO::getTokens).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Maps a User entity object to a UserDTO, getting rid of unnecessary data for displaying.
     * @param user the user which will be mapped to a DTO
     * @return the DTO created from the User
     */
    private UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setTokens(user.getTokens());
        return userDTO;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user!"));
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
        user.setTokens(0);

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

        Badge badge = new Badge();
        String badge_name = "Registered";
        badge = badgeRepository.findByName(badge_name);
        if (badge == null) {
            badge = checkBadge(badge_name);
        }
        user.addBadge(badge);
        giveReward(user, badge.getReward());
        userRepository.save(user);
    }

    /**
     * Checks for the Role name given and saves the newly created Role with that name.
     * @param role_name the name by which to check
     * @return a newly created Role object with the given role name
     */
    private Role checkRole(String role_name) {
        Role role = new Role(role_name);
        return roleRepository.save(role);
    }

    /**
     * Checks for the badge name given and saves the newly created badge with that name.
     * @param badge_name the name by which to check
     * @return a newly created Badge object with the given badge name
     */
    private Badge checkBadge(String badge_name) {
        Badge badge = new Badge();
        badge.setName(badge_name);
        if (Objects.equals(badge_name, "Registered")) {
            badge.setDescription("Earned for successfully registering as an adventurer.");
            badge.setReward(100);
        }
        else if (Objects.equals(badge_name, "Newbie")) {
            badge.setDescription("Earned for possessing over 250 tokens at any point in time.");
            badge.setReward(25);
        }
        else if (Objects.equals(badge_name, "Apprentice")) {
            badge.setDescription("Earned for possessing over 500 tokens at any point in time.");
            badge.setReward(50);
        }
        else if (Objects.equals(badge_name, "Specialist")) {
            badge.setDescription("Earned for possessing over 1000 tokens at any point in time.");
            badge.setReward(100);
        }
        else if (Objects.equals(badge_name, "Expert")) {
            badge.setDescription("Earned for possessing over 2500 tokens at any point in time.");
            badge.setReward(250);
        }
        else if (Objects.equals(badge_name, "Master")) {
            badge.setDescription("Earned for possessing over 5000 tokens at any point in time.");
            badge.setReward(500);
        }
        else if (Objects.equals(badge_name, "Godlike")) {
            badge.setDescription("Earned for possessing over 10000 tokens at any point in time.");
            badge.setReward(50);
        }
        else if (Objects.equals(badge_name, "Quid Pro Nothing")) {
            badge.setDescription("Earned for reaching 0 tokens. Maybe try solving some quests?");
            badge.setReward(25);
        }
        else if (Objects.equals(badge_name, "Bug, Destroyer of Apps")) {
            badge.setDescription("Earned for somehow having negative tokens. Go... you?");
            badge.setReward(1);
        }
        return badgeRepository.save(badge);
    }

    @Override
    public void removeUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        userRepository.deleteById(user.getId());
    }

    /**
     * Gives tokens to a User entity. Checks if User is eligible for badges. Adds badge to user if so. Saves.
     * @param user the user who will receive the tokens
     * @param tokens the tokens to be given to the user
     */
    @Override
    public void giveReward(User user, int tokens) {
        user.setTokens(user.getTokens() + tokens);
        if (user.getTokens() >= 250) {
            int userTokens = user.getTokens();
            String badge_name = "Newbie";
            if (userTokens >= 10000) {
                badge_name = "Godlike";
            }
            else if (userTokens >= 5000) {
                badge_name = "Master";
            }
            else if (userTokens >= 2500) {
                badge_name = "Expert";
            }
            else if (userTokens >= 1000) {
                badge_name = "Specialist";
            }
            else if (userTokens >= 500) {
                badge_name = "Apprentice";
            }
            Badge badge = new Badge();
            badge = badgeRepository.findByName(badge_name);
            if (badge == null) {
                badge = checkBadge(badge_name);
            }
            if (!user.getBadges().contains(badge)) {
                user.addBadge(badge);
                user.setTokens(userTokens + badge.getReward());
                userRepository.save(user);
            }
        }
    }

    /**
     * Takes tokens from a User entity. Checks if User is now eligible for badges. Adds badge to user if so. Saves.
     * @param username the username of the user who will lose the tokens
     * @param tokens the tokens to be taken from the user
     */
    @Override
    public void takeReward(String username, int tokens) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setTokens(user.getTokens() - tokens);
            if (user.getTokens() <= 0) {
                Badge badge = new Badge();
                String badge_name = null;
                if (user.getTokens() < 0) {
                    badge_name = "Bug, Destroyer of Worlds";
                }
                else if (user.getTokens() == 0) {
                    badge_name = "Quid Pro Nothing";
                }
                badge = badgeRepository.findByName(badge_name);
                if (badge == null) {
                    badge = checkBadge(badge_name);
                }
                user.addBadge(badge);
                giveReward(user, badge.getReward());
            }

            userRepository.save(user);
        }
    }
}
