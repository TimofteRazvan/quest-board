package com.example.QuestBoard.Entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestDTO {
    private Long id;
    @NotEmpty(message = "Title must not be blank!")
    private String title;
    @NotEmpty(message = "Description must not be blank!")
    private String description;
    private int reward;
}