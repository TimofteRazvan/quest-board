package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Role;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Entity.UserDTO;
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
                .map(this::mapDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapDTO(User user) {
        UserDTO userDTO = new UserDTO();
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

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRole();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    private Role checkRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
