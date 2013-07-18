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

public class Bresenham extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	private static final int PSIZE = 10;
	private static final int WIDTH = APPLICATION_WIDTH / PSIZE;
	private static final int HEIGHT = APPLICATION_HEIGHT / PSIZE;

	public void run() {
		drawLine(0, 0, WIDTH, HEIGHT);
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int xs = x0 < x1 ? 1 : -1;
		int ys = y0 < y1 ? 1 : -1;
		int e = dx - dy;
		while (true) {
			drawPoint(x0, y0);
			if (x0 == x1 && y0 == y1){
				
			}
		}
	}

	public void drawLine2(int x0, int y0, int x1, int y1) {
		boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
		if (steep) {
			int t = x0;
			x0 = y0;
			y0 = t;
			t = y0;
			y0 = x0;
			x0 = t;
		}
		if (x0 > x1) {
			int t = x1;
			x1 = x0;
			x0 = t;
			t = y1;
			y1 = y0;
			y0 = t;
		}
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		int error = dy / 2;
		int ys = y0 < y1 ? 1 : -1;
		int y = y0;
		for (int x = x0; x <= x1; x++) {
			System.out.println(dx + "," + dy + ": (" + ys + ") (" + error
					+ ") " + x + "," + y);
			if (steep)
				drawPoint(y, x);
			else
				drawPoint(x, y);
			error -= dx;
			if (error < 0) {
				y += ys;
				error += dy;
			}
		}
	}

	public void drawPoint(int x, int y) {
		GRect p = new GRect(x * PSIZE, y * PSIZE, PSIZE, PSIZE);
		p.setFillColor(Color.BLACK);
		p.setFilled(true);
		add(p);
	}
}
