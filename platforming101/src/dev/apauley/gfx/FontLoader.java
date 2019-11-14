package dev.apauley.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/*
 * Loads Fonts
 */

public class FontLoader {
	
	//Load font from file, specifying size
	public static Font loadFont(String path, float size) {

		try {
			//TRUETYPE_FONT = ttf fonts
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return null;
	}

}
