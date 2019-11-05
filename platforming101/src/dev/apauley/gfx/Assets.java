package dev.apauley.gfx;

import java.awt.image.BufferedImage;

/*
 * Will Load Everything into Game only once (then can use assets at any time)
 */

public class Assets {

	//Width and Height of tiles/player (except for heightBig which is double the size)
	private static final int width = 32, height = width;

	//Holds all tiles/items/etc.
	public static BufferedImage player, dirt, grass, stone, tree; 		

	//Initializes Assets
	public static void init() {
		
		//Title Screen sprites, namely the big "Super Mario Brothers" menu
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
	
		player 	= sheet.crop(width * 0, height * 0, width, height);
		dirt 	= sheet.crop(width * 1, height * 0, width, height);
		grass 	= sheet.crop(width * 2, height * 0, width, height);
		stone 	= sheet.crop(width * 3, height * 0, width, height);
		tree 	= sheet.crop(width * 0, height * 1, width, height);
		
	}

}
