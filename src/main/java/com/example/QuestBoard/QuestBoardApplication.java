package com.example.QuestBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestBoardApplication.class, args);
	}
	// TODO: Changed user delete to be deleteById. Check if it still works!!!

	// TODO: MIGHT BE BETTER TO MAKE SOLUTION A MANY-TO-ONE WITH QUESTS, NO RELATION TO USER
	// TODO: INSTEAD, SOLUTION HAS "USERNAME" FIELD THAT CAN BE USED TO FIND POSTER

	// TODO: Quest Delete
	// TODO: Solution binding & Quest Solutions View
	// TODO: Proper cascading
	// TODO: Rewards
}
