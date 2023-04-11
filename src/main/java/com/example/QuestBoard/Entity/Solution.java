package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static org.hibernate.Length.LOB_DEFAULT;

/**
 * The Solution class represents a solution that can be written for a Quest by a User. Multiple Users can write solutions
 * for multiple Quests. Contains ID, User (who writes the solution), Quest (for which the solution is written),
 * ENTRY (text that represents the written solution itself).
 */
@Entity(name = "Solution")
@Table(name = "solutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;
    @Column(length = LOB_DEFAULT)
    private String entry;

    public Solution(User user, Quest quest, String entry) {
        this.user = user;
        this.quest = quest;
        this.entry = entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Objects.equals(id, solution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", user=" + user +
                ", quest=" + quest +
                ", entry='" + entry + '\'' +
                '}';
    }
}
