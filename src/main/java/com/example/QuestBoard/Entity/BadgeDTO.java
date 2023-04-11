package com.example.QuestBoard.Entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The BadgeDTO class represents a Badge class, but without the extra information that is useless for the sake of
 * displaying and listing. Contains only ID, NAME, DESCRIPTION, REWARD.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDTO {
    private Long id;
    @NotEmpty
    private String name;
    private String description;
    private int reward;

    public BadgeDTO(String name, String description, int reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "BadgeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                '}';
    }
}
