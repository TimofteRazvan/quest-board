package com.example.QuestBoard.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The SolutionDTO class represents a Solution class, but without the extra information that is useless for the sake of
 * displaying and listing. Contains only ID, ENTRY, QUEST (equivalent to Quest.getId() for Solution)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTO {
    private Long id;
    private String entry;
    private Long quest;

    @Override
    public String toString() {
        return "SolutionDTO{" +
                "id=" + id +
                ", entry='" + entry + '\'' +
                '}';
    }
}
