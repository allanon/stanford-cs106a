/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {

	ArrayList<String> words;

	public HangmanLexicon() {
		words = new ArrayList<String>();
		try {
			BufferedReader fp = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			String line;
			while ((line = fp.readLine()) != null) {
				words.add(line);
			}
			fp.close();
		} catch (Exception e) {
		}
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		if (index < 0 || index >= words.size())
			throw new ErrorException("getWord: Illegal index");
		return words.get(index);
	};
}
