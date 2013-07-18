/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		version1();
	}

	// *.......*
	// .*.....*.
	// ..*...*..
	// ...*.*...
	// ....*....
	public void version1() {
		// First part of the initial condition: drop a beeper on one side of the
		// world.
		putBeeper();

		// Need this if statement to handle 1 tile width worlds.
		if (frontIsClear()) {

			// Finish setting up the initial condition: standing on the other
			// side of the world.
			while (frontIsClear())
				move();
			turnAround();

			// Now swap the condition, so that we're standing on the other side
			// of the world, one tile closer to the center than we were before.
			// Keep doing this until moving only one tile will leave us on the
			// second beeper.
			while (noBeepersPresent()) {
				putBeeper();
				move();
				if (noBeepersPresent()) {
					while (noBeepersPresent())
						move();
					pickBeeper();
					turnAround();
					move();
				}
			}

			// Pick up the second beeper, and we're done!
			pickBeeper();
		}
	}

	// .*******.
	// .******..
	// ..*****..
	// ..****...
	// ...***...
	// ...**....
	// ....*....
	public void version2() {
		if (frontIsClear()) {

			// Initial condition: beepers on every tile except the outermost.
			move();
			while (frontIsClear()) {
				putBeeper();
				move();
			}
			turnAround();
			move();

			// Remove outermost beeper, then go to the far end.
			while (beepersPresent()) {
				pickBeeper();
				move();
				while (beepersPresent())
					move();
				turnAround();
				move();
			}
		}

		putBeeper();
	}
}
