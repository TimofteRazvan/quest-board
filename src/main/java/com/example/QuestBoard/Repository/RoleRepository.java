package com.example.QuestBoard.Repository;

import com.example.QuestBoard.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Gets a Role object that has the same name as the argument string
     * @param name the name by which to search for existing Role
     * @return the Role object if it exists, null otherwise
     */
    Role findByName(String name);
}
