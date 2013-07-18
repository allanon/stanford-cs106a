/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private int bricksRemaining;
	private int bricksTotal;
	private GRect paddle;
	private GOval ball;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx, vy;
	private int pauseMsec;

	public void init() {
		Color colors[] = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
				Color.CYAN };
		bricksTotal = 0;
		for (int y = 0; y < NBRICK_ROWS; y++) {
			for (int x = 0; x < NBRICKS_PER_ROW; x++) {
				GRect brick = new GRect((BRICK_WIDTH + BRICK_SEP) * x
						+ BRICK_SEP / 2, (BRICK_HEIGHT + BRICK_SEP) * y
						+ BRICK_Y_OFFSET, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setColor(colors[y / 2]);
				brick.setFillColor(colors[y / 2]);
				brick.setFilled(true);
				add(brick);
				bricksTotal++;
			}
		}
		bricksRemaining = bricksTotal;

		paddle = new GRect(0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
				PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		paddle.setFillColor(Color.BLACK);
		add(paddle);
		addMouseListeners();

		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS,
				BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		ball.setFillColor(Color.BLACK);
		add(ball);

		// Initialize ball velocity.
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		vy = 3.0;

		pauseMsec = 20;
		
		waitForClick();
	}

	/** Runs the Breakout program. */
	public void run() {
		int turnsLeft = NTURNS;
		while (turnsLeft > 0 && bricksRemaining > 0) {
			ball.move(vx, vy);
			if (ball.getX() < 0)
				vx = -vx;
			if (ball.getX() + BALL_RADIUS * 2 > WIDTH)
				vx = -vx;
			if (ball.getY() < 0)
				vy = -vy;
			if (ball.getY() + BALL_RADIUS * 2 > HEIGHT) {
				turnsLeft--;
				if (turnsLeft > 0) {
					ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2
							- BALL_RADIUS);
					waitForClick();
				}
			}
			GObject collider = getCollidingObject();
			if (collider == paddle) {
				paddleCollisionAltersVx();
				ball.move(0, (paddle.getY() - BALL_RADIUS * 2) - ball.getY());
				if (vy > 0)
					vy = -vy;
			} else if (collider != null) {
				bricksRemaining--;
				remove(collider);
				vy = -vy;
			}
			brickCountAffectsPauseMsec();
			pause(pauseMsec);
		}
	}

	private void paddleCollisionAltersVx() {
		double pct = (ball.getX() - paddle.getX()) / PADDLE_WIDTH;
		if (pct < 0)
			pct = 0;
		if (pct > 1)
			pct = 1;
		if (pct < 0.5)
			vx = -4 * (0.5 - pct);
		if (pct >= 0.5)
			vx = 4 * (pct - 0.5);
	}

	private void brickCountAffectsPauseMsec() {
		pauseMsec = 8 + 8 * bricksRemaining / bricksTotal;
	}

	public GObject getCollidingObject() {
		GObject object = null;
		if (object == null)
			object = getElementAt(ball.getX(), ball.getY());
		if (object == null)
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		if (object == null)
			object = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		if (object == null)
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()
					+ 2 * BALL_RADIUS);
		return object;
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX() - PADDLE_WIDTH / 2;
		if (x < 0)
			x = 0;
		if (x > WIDTH - PADDLE_WIDTH)
			x = WIDTH - PADDLE_WIDTH;
		paddle.move(x - paddle.getX(), 0);
	}
}
