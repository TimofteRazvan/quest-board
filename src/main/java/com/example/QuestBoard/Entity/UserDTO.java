package com.example.QuestBoard.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty(message = "Email must not be blank!")
    @Email
    private String email;
    private Integer tokens;
    @NotEmpty(message = "Password must not be blank!")
    private String password;

    public UserDTO(String username, String email, Integer tokens, String password) {
        this.username = username;
        this.email = email;
        this.tokens = tokens;
        this.password = password;
    }
}
