/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;

public class Hangman extends ConsoleProgram {
	private HangmanCanvas canvas;
	private HangmanLexicon lexicon;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		canvas.reset();
		println("Welcome to Hangman!");
		lexicon = new HangmanLexicon();
		String word = lexicon.getWord((int) (Math.random() * lexicon.getWordCount()));

		// Convert the word into the dashed version.
		String dashWord = "";
		for (int i = 0; i < word.length(); i++)
			dashWord += '-';

		int guessesLeft = 8;
		int dashesLeft = dashWord.length();
		while (guessesLeft > 0 && dashesLeft > 0) {
			println("The word now looks like this: " + dashWord);
			canvas.displayWord(dashWord);
			println("You have " + guessesLeft + " guesses left.");
			String input = this.readLine("Your guess: ");
			if (input.length() != 1) {
				println("Guesses must be exactly one letter. Please try again.");
				continue;
			}
			dashesLeft = 0;
			char inputChar = input.toUpperCase().charAt(0);
			String newDashWord = "";
			int found = 0;
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == inputChar) {
					newDashWord += word.charAt(i);
					found++;
				} else if (dashWord.charAt(i) != '-') {
					newDashWord += word.charAt(i);
				} else {
					newDashWord += '-';
					dashesLeft++;
				}
			}
			if (found == 0) {
				guessesLeft--;
				println("There are no " + inputChar + "'s in the word.");
				canvas.noteIncorrectGuess(inputChar);
			} else {
				dashWord = newDashWord;
				println("That guess is correct.");
			}
		}
		if (dashesLeft == 0) {
			canvas.displayWord(word);
			println("You guessed the word: " + word);
			println("You win.");
		} else {
			println("The word was: " + word);
			println("You lose.");
		}
	}

}
