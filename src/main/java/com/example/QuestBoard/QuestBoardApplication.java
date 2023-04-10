package com.example.QuestBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestBoardApplication.class, args);
	}
	// TODO: For quest-solutions, either do form -> post -> solution object OR send solution id like in view details


	// TODO: Rewards and choosing correct solution + Quest Delete
	// TODO: Proper cascading for users & user quests
	// TODO: Permissions, add admin role on specific conditions, can delete others' quests, users
}
