/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		while (true) {
			dropAndMove();
			turnLeft();
			if (noBeepersPresent()) {
				if (frontIsBlocked())
					return;
				move();
				turnLeft();
				dropAndMove();
				turnRight();
				if (frontIsBlocked())
					return;
				move();
				turnRight();
			} else {
				if (frontIsBlocked())
					return;
				move();
				turnLeft();
				moveAndDrop();
				turnRight();
				if (frontIsBlocked())
					return;
				move();
				turnRight();
			}
		}
	}

	private void dropAndMove() {
		while (true) {
			putBeeper();
			if (frontIsBlocked())
				break;
			move();
			if (frontIsBlocked())
				break;
			move();
		}
	}

	private void moveAndDrop() {
		while (true) {
			if (frontIsBlocked())
				break;
			move();
			putBeeper();
			if (frontIsBlocked())
				break;
			move();
		}
	}
}
