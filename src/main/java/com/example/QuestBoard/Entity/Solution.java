package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
