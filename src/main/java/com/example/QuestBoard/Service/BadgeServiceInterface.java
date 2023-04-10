package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Badge;
import com.example.QuestBoard.Entity.BadgeDTO;

import java.util.List;

public interface BadgeServiceInterface {
    List<BadgeDTO> findAllBadges();

    void saveBadge(BadgeDTO badgeDTO);
}
