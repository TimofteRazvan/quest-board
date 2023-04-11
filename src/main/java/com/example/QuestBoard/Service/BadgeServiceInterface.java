package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Badge;
import com.example.QuestBoard.Entity.BadgeDTO;

import java.util.List;

public interface BadgeServiceInterface {
    /**
     * Get all the Badge entities from the BadgeRepository converted into DTOs.
     * @return the list of badges from the database, converted to DTOs
     */
    List<BadgeDTO> findAllBadges();

    /**
     * Creates a new Badge object and sets its values according to the BadgeDTO given, then persists it.
     * @param badgeDTO the BadgeDTO object which will be saved as a Badge into the database
     */
    void saveBadge(BadgeDTO badgeDTO);

    /**
     * Removes a Badge from the database if it matches the id argument given
     * @param id the id by which to search and remove the badge
     */
    void removeBadgeById(Long id);
}
