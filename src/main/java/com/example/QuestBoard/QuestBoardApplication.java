package com.example.QuestBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestBoardApplication.class, args);
	}
	// TODO: Check user deletion!

	// TODO: Fix IDs in links so that quest ids are automatically filled
	// TODO: Quest Delete
	// TODO: Proper cascading
	// TODO: Rewards and choosing correct solution
}
