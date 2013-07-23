/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.util.Iterator;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private GLabel wordLabel;
	private String guesses;
	private GLabel guessesLabel;
	private GCompound body;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		add(new GLine(MARGIN, MARGIN, MARGIN, MARGIN + SCAFFOLD_HEIGHT));
		add(new GLine(MARGIN, MARGIN, MARGIN + BEAM_LENGTH, MARGIN));
		add(new GLine(MARGIN + BEAM_LENGTH, MARGIN, MARGIN + BEAM_LENGTH, MARGIN + ROPE_LENGTH));
		body = new GCompound();
		body.setLocation(MARGIN + BEAM_LENGTH, MARGIN + ROPE_LENGTH);
		add(body);
		wordLabel = new GLabel("");
		wordLabel.setFont("*-bold-18");
		add(wordLabel);
		guesses = "";
		guessesLabel = new GLabel(guesses);
		add(guessesLabel);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		wordLabel.setLabel(word);
		wordLabel.setLocation((getWidth() - wordLabel.getWidth()) / 2, MARGIN + SCAFFOLD_HEIGHT + WORD_SPACING);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		guesses += letter;
		guessesLabel.setLabel(guesses);
		guessesLabel.setLocation((getWidth() - guessesLabel.getWidth()) / 2, wordLabel.getY() + wordLabel.getHeight()
				+ WORD_SPACING);
		if (guesses.length() == 1)
			body.add(buildHead());
		if (guesses.length() == 2)
			body.add(buildTorso());
		if (guesses.length() == 3)
			body.add(buildLeftArm());
		if (guesses.length() == 4)
			body.add(buildRightArm());
		if (guesses.length() == 5)
			body.add(buildLeftLeg());
		if (guesses.length() == 6)
			body.add(buildRightLeg());
		if (guesses.length() == 7)
			body.add(buildLeftFoot());
		if (guesses.length() == 8)
			body.add(buildRightFoot());
	}

	private GObject buildHead() {
		return new GOval(-HEAD_RADIUS, 0, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
	}

	private GObject buildTorso() {
		return new GLine(0, HEAD_RADIUS * 2, 0, HEAD_RADIUS * 2 + BODY_LENGTH);
	}

	private GCompound buildLeftArm() {
		GCompound arm = new GCompound();
		arm.add(new GLine(0, 0, UPPER_ARM_LENGTH, 0));
		arm.add(new GLine(0, 0, 0, LOWER_ARM_LENGTH));
		arm.setLocation(-arm.getWidth(), HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
		return arm;
	}

	private GObject buildRightArm() {
		return hFlipGObject(hFlipGCompound(buildLeftArm()), 0);
	}

	private GCompound buildLeftLeg() {
		GCompound leg = new GCompound();
		leg.add(new GLine(0, 0, HIP_WIDTH, 0));
		leg.add(new GLine(0, 0, 0, LEG_LENGTH));
		leg.setLocation(-HIP_WIDTH, HEAD_RADIUS * 2 + BODY_LENGTH);
		return leg;
	}

	private GObject buildRightLeg() {
		return hFlipGObject(hFlipGCompound(buildLeftLeg()), 0);
	}

	private GObject buildLeftFoot() {
		return new GLine(-HIP_WIDTH - FOOT_LENGTH, HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH, -HIP_WIDTH, HEAD_RADIUS * 2
				+ BODY_LENGTH + LEG_LENGTH);
	}

	private GObject buildRightFoot() {
		return hFlipGObject(buildLeftFoot(), 0);
	}

	/* Flip a GCompound horizontally over its center. */
	private GCompound hFlipGCompound(GCompound c) {
		for (Iterator<GObject> i = c.iterator(); i.hasNext();)
			hFlipGObject(i.next(), c.getWidth() / 2);
		return c;
	}

	/* Flip an object horizontally over the given x coordinate. */
	private GObject hFlipGObject(GObject o, double x) {
		o.setLocation(2 * x - o.getWidth() - o.getX(), o.getY());
		return o;
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int MARGIN = 30;
	private static final int WORD_SPACING = 30;
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
