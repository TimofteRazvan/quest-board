package com.example.QuestBoard.Repository;

import com.example.QuestBoard.Entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByName(String name);
}
