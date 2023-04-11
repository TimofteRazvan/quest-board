package com.example.QuestBoard.Service;

import com.example.QuestBoard.Entity.Badge;
import com.example.QuestBoard.Entity.BadgeDTO;
import com.example.QuestBoard.Repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BadgeService implements BadgeServiceInterface {
    @Autowired
    BadgeRepository badgeRepository;

    @Override
    public List<BadgeDTO> findAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return badges.stream()
                .map(this::mapBadgeToBadgeDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Badge to a BadgeDTO, getting rid of unnecessary data for displaying.
     * @param badge the badge which will be mapped to a DTO
     * @return the DTO created from the Badge object
     */
    public BadgeDTO mapBadgeToBadgeDTO(Badge badge) {
        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setId(badge.getId());
        badgeDTO.setName(badge.getName());
        badgeDTO.setDescription(badge.getDescription());
        badgeDTO.setReward(badge.getReward());
        return badgeDTO;
    }

    @Override
    public void saveBadge(BadgeDTO badgeDTO) {
        Badge badge = new Badge();
        badge.setName(badgeDTO.getName());
        badge.setDescription(badgeDTO.getDescription());
        badge.setReward(badgeDTO.getReward());
        badgeRepository.save(badge);
    }

    @Override
    public void removeBadgeById(Long id) {
        Badge badge = badgeRepository.findById(id).orElseThrow(() -> new RuntimeException("No such badge!"));
        badgeRepository.deleteById(badge.getId());
    }
}
