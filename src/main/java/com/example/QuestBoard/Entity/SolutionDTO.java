package com.example.QuestBoard.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
