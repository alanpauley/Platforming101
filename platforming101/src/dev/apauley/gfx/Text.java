package dev.apauley.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/*
 * Handles Text
 */

public class Text {

	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		
		//If Centered = true, change x and y so it refers to a different point
		if(center) {

			//gets parameters of font (height, etc.)
			FontMetrics fm = g.getFontMetrics(font);
			
			//Get center of x
			x = xPos - fm.stringWidth(text) / 2;
						
			//Get center of y
			//Note: getAscent() is a correction value  (the amount of pixels the font should be draw above the baseline. Like an offset?)
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		g.drawString(text, x, y);
	}
	
}
