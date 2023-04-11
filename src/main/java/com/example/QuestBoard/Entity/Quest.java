package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Quest class represents quests that users can make, delete, view and give solutions to.
 * They have an ID, TITLE, DESCRIPTION, REWARD, USER Object in a One-To-One relationship,
 * LIST of Solutions in a One-To-Many relationship.
 */
@Entity(name = "Quest")
@Table(name = "quests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private int reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "quest")
    private List<Solution> questSolutions = new ArrayList<>();

    public Quest(String title, String description, int reward, User user) {
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.user = user;
    }

    public void addSolution(Solution solution) {
        questSolutions.add(solution);
        solution.setQuest(this);
    }

    public void removeSolution(Solution solution) {
        questSolutions.remove(solution);
        solution.setQuest(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return Objects.equals(id, quest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", user=" + user +
                ", questSolutions=" + questSolutions +
                '}';
    }
}
