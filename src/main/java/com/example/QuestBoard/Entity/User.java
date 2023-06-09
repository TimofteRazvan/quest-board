package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The User class represents those who will use the application. Users can have multiple roles, badges, quests and
 * solutions. Also contains ID, USERNAME, EMAIL, TOKENS (Points), PASSWORD, LIST of Quests in a One-To-Many relationship,
 * LIST of Solutions in a One-To-Many relationship, LIST of Roles in a Many-To-Many relationship, and LIST of Badges in
 * Many-To-Many relationship.
 */
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
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private int tokens;
    @Column(nullable = false, unique = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Quest> userQuests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Solution> userSolutions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="users_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "users_badges",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "badge_id"))
    private List<Badge> badges = new ArrayList<>();


    public User(String username, String email, int tokens, String password) {
        this.username = username;
        this.email = email;
        this.tokens = tokens;
        this.password = password;
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

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public void removeBadge(Badge badge) {
        badges.remove(badge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return username;
    }
}
