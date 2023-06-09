package com.example.QuestBoard.Security;

import com.example.QuestBoard.Entity.Role;
import com.example.QuestBoard.Entity.User;
import com.example.QuestBoard.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Custom UserDetailsService implements and overrides the UserDetailsService methods so that they may be used instead.
 * Allows for changing how SpringSecurity deals with the currently logged-in user.
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    mapAuthorities(user.getRoles()));
        }
        else {
            throw new UsernameNotFoundException("Invalid Username or Password!");
        }
    }

    /**
     * Gives simple authorities for every role (ADMIN, USER).
     * @param roles the roles that the user has
     * @return the mapped roles
     */
    private Collection<? extends GrantedAuthority> mapAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
