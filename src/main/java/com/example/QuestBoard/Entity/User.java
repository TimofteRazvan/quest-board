package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    private String username;
    private String email;
    private int tokens;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Quest> userQuests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Solution> userSolutions = new ArrayList<>();

    public User(String username, String email, int tokens) {
        this.username = username;
        this.email = email;
        this.tokens = tokens;
    }

    public void addQuest(Quest quest) {
        this.userQuests.add(quest);
        quest.setUser(this);
    }

    public void removeQuest(Quest quest) {
        this.userQuests.remove(quest);
        quest.setUser(null);
    }

    public void addSolution(Solution solution) {
        this.userSolutions.add(solution);
        solution.setUser(this);
    }

    public void removeSolution(Solution solution) {
        this.userSolutions.remove(solution);
        solution.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
