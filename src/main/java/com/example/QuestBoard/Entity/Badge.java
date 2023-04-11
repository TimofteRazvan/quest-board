package com.example.QuestBoard.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * The Badge class represents badges that users can earn. They have an ID, NAME, DESCRIPTION, REWARD, LIST of users in
 * a Many-To-Many relationship.
 */
@Entity(name = "Badge")
@Table(name = "badges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private int reward;

    @ManyToMany(mappedBy = "badges")
    private List<User> users;

    public Badge(String name, String description, int reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    /**
     * Adds a User to the List of users mapped to the badge
     * @param user the user to be added
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes a User from the List of users mapped to the badge
     * @param user the user to be removed
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return id.equals(badge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Badge{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                ", users=" + users +
                '}';
    }
}
