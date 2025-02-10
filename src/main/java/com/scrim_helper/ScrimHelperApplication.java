package com.scrim_helper;

import com.scrim_helper.controller.OpGgScraper;

import java.util.Scanner;

public class ScrimHelperApplication {

	private static final OpGgScraper opGgScraper = new OpGgScraper();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the userName with '#'");
		String userName = scanner.nextLine();
		String userId = opGgScraper.findPlayerId(userName);

		scanner.close();
		if(userId.isBlank()) {
			System.out.println("No user id found");
		}
		else {
			opGgScraper.OpGgWebScraper(userId);
		}
	}

}
