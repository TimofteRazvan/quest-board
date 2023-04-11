package com.example.QuestBoard.Repository;

import com.example.QuestBoard.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Gets a User object that has the same email as the argument string
     * @param email the name by which to search for existing User
     * @return the User object if it exists, null otherwise
     */
    User findByEmail(String email);
    /**
     * Gets a User object that has the same username as the argument string
     * @param username the username by which to search for existing User
     * @return the User object if it exists, null otherwise
     */
    User findByUsername(String username);
}
