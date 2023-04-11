package com.example.QuestBoard.Repository;

import com.example.QuestBoard.Entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    /**
     * Gets a Badge object that has the same name as the argument string
     * @param name the name by which to search for existing Badge
     * @return the Badge object if it exists, null otherwise
     */
    Badge findByName(String name);
}
