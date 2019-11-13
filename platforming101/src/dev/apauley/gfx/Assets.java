package dev.apauley.gfx;

import java.awt.image.BufferedImage;

/*
 * Will Load Everything into Game only once (then can use assets at any time)
 */

public class Assets {

	//Width and Height of tiles/player (except for heightBig which is double the size)
	private static final int width = 32, height = width;

	//Holds all tiles/items/etc.
	public static BufferedImage player, dirt, grass, stone;
	
	//Holds all static entities
	public static BufferedImage tree, rock;

	//player Animations
	public static BufferedImage[] player_down, player_up, player_right, player_left; 		

	//start game button
	public static BufferedImage[] btn_start;
	
	//Initializes Assets
	public static void init() {
		
		//Title Screen sprites, namely the big "Super Mario Brothers" menu
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
	
		//Start game buttons
		btn_start = new BufferedImage[2];
			btn_start[0] = sheet.crop(width * 6, height * 4, width * 2, height);
			btn_start[1] = sheet.crop(width * 6, height * 5, width * 2, height);
		
		//Player Down
		player_down = new BufferedImage[2];
			player_down[0] = sheet.crop(width * 4, height * 0, width, height);
			player_down[1] = sheet.crop(width * 5, height * 0, width, height);
		//Player Up
		player_up = new BufferedImage[2];
			player_up[0] = sheet.crop(width * 6, height * 0, width, height);
			player_up[1] = sheet.crop(width * 7, height * 0, width, height);
		//Player Right
		player_right = new BufferedImage[2];
			player_right[0] = sheet.crop(width * 4, height * 1, width, height);
			player_right[1] = sheet.crop(width * 5, height * 1, width, height);
		//Player Left
		player_left = new BufferedImage[2];
			player_left[0] = sheet.crop(width * 6, height * 1, width, height);
			player_left[1] = sheet.crop(width * 7, height * 1, width, height);
		
		dirt 	= sheet.crop(width * 1, height * 0, width, height);
		grass 	= sheet.crop(width * 2, height * 0, width, height);
		stone 	= sheet.crop(width * 3, height * 0, width, height);

	//Static Entities
		tree 	= sheet.crop(width * 0, height * 0, width, height * 2);
		rock 	= sheet.crop(width * 0, height * 2, width, height);
	}

}
