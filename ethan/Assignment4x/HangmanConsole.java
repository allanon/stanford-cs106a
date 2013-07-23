/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class HangmanConsole extends ConsoleProgram {
	HangmanLexicon lexicon;

	public void run() {
		println("Welcome to Hangman!");
		lexicon = new HangmanLexicon();
		String word = lexicon.getWord((int) (Math.random() * lexicon
				.getWordCount()));

		// Convert the word into the dashd version.
		String dashWord = "";
		for (int i = 0; i < word.length(); i++)
			dashWord += '-';

		int guessesLeft = 8;
		int dashesLeft = dashWord.length();
		while (guessesLeft > 0 && dashesLeft > 0) {
			println("The word now looks like this: " + dashWord);
			println("You have " + guessesLeft + " guesses left.");
			String input = this.readLine("Your guess: ");
			if (input.length() != 1) {
				println("Guesses must be exactly one letter. Please try again.");
				continue;
			}
			dashesLeft = 0;
			char inputChar = input.toUpperCase().charAt(0);
			String newDashWord = "";
			for (int i = 0; i < word.length(); i++) {
				if (dashWord.charAt(i) != '-' || word.charAt(i) == inputChar) {
					newDashWord += word.charAt(i);
				} else {
					newDashWord += '-';
					dashesLeft++;
				}
			}
			if (newDashWord.equals(dashWord)) {
				guessesLeft--;
				println("There are no " + inputChar + "'s in the word.");
			} else {
				dashWord = newDashWord;
				println("That guess is correct.");
			}
		}
		if (dashesLeft == 0) {
			println("You guessed the word: " + word);
			println("You win.");
		} else {
			println("The word was: " + word);
			println("You lose.");
		}
	}

}
