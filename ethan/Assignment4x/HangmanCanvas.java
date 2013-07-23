/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	GLabel wordLabel;
	String guesses;
	GLabel guessesLabel;
	GObject head;
	GObject body;
	GObject leftArm;
	GObject rightArm;
	GObject leftLeg;
	GObject rightLeg;
	GObject leftFoot;
	GObject rightFoot;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		add(new GLine(MARGIN, MARGIN, MARGIN, MARGIN + SCAFFOLD_HEIGHT));
		add(new GLine(MARGIN, MARGIN, MARGIN + BEAM_LENGTH, MARGIN));
		add(new GLine(MARGIN + BEAM_LENGTH, MARGIN, MARGIN + BEAM_LENGTH, MARGIN + ROPE_LENGTH));
		wordLabel = new GLabel("");
		wordLabel.setFont("*-bold-18");
		add(wordLabel);
		guesses = new String();
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
		wordLabel.setLocation((getWidth() - wordLabel.getWidth() - MARGIN * 2) / 2, MARGIN + SCAFFOLD_HEIGHT + WORD_SPACING);
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
		guessesLabel.setLocation((getWidth() - guessesLabel.getWidth() - MARGIN * 2) / 2, wordLabel.getY() + wordLabel.getHeight()
				+ WORD_SPACING);
		if (guesses.length() >= 1 && head == null)
			add(head = new GOval(MARGIN + BEAM_LENGTH - HEAD_RADIUS, MARGIN + ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2));
		if (guesses.length() >= 2 && body == null)
			add(body = new GLine(MARGIN + BEAM_LENGTH, head.getY() + head.getHeight(), MARGIN + BEAM_LENGTH, head.getY()
					+ head.getHeight() + BODY_LENGTH));
		if (guesses.length() >= 3 && leftArm == null) {
			GCompound arm = new GCompound();
			arm.add(new GLine(0, 0, UPPER_ARM_LENGTH, 0));
			arm.add(new GLine(0, 0, 0, LOWER_ARM_LENGTH));
			arm.setLocation(MARGIN + BEAM_LENGTH - arm.getWidth(), body.getY() + ARM_OFFSET_FROM_HEAD);
			add(leftArm = arm);
		}
		if (guesses.length() >= 4 && rightArm == null) {
			GCompound arm = new GCompound();
			arm.add(new GLine(0, 0, UPPER_ARM_LENGTH, 0));
			arm.add(new GLine(UPPER_ARM_LENGTH, 0, UPPER_ARM_LENGTH, LOWER_ARM_LENGTH));
			arm.setLocation(MARGIN + BEAM_LENGTH, body.getY() + ARM_OFFSET_FROM_HEAD);
			add(leftArm = arm);
		}
		if (guesses.length() >= 5 && leftLeg == null) {
			GCompound leg = new GCompound();
			leg.add(new GLine(0, 0, HIP_WIDTH, 0));
			leg.add(new GLine(0, 0, 0, LEG_LENGTH));
			leg.setLocation(MARGIN + BEAM_LENGTH - leg.getWidth(), body.getY() + body.getHeight());
			add(leftLeg = leg);
		}
		if (guesses.length() >= 6 && rightLeg == null) {
			GCompound leg = new GCompound();
			leg.add(new GLine(0, 0, HIP_WIDTH, 0));
			leg.add(new GLine(HIP_WIDTH, 0, HIP_WIDTH, LEG_LENGTH));
			leg.setLocation(MARGIN + BEAM_LENGTH, body.getY() + body.getHeight());
			add(rightLeg = leg);
		}
		if (guesses.length() >= 7 && leftFoot == null)
			add(leftFoot = new GLine(leftLeg.getX(), rightLeg.getY() + rightLeg.getHeight(), leftLeg.getX() - FOOT_LENGTH, rightLeg.getY()
					+ rightLeg.getHeight()));
		if (guesses.length() >= 8 && rightFoot == null)
			add(leftFoot = new GLine(rightLeg.getX() + rightLeg.getWidth(), rightLeg.getY() + rightLeg.getHeight(), rightLeg.getX()
					+ rightLeg.getWidth() + FOOT_LENGTH, rightLeg.getY() + rightLeg.getHeight()));
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
