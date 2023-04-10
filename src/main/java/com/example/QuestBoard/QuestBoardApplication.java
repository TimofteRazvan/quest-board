package com.example.QuestBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestBoardApplication.class, args);
	}

	// TODO: Quest Delete after reward given
	// TODO: Proper cascading for users & user quests
	// TODO: Permissions, add admin role on specific conditions, can delete others' quests, users
}
