package dev.apauley.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

/*
 * Will Load Everything into Game only once (then can use assets at any time)
 */

public class Assets {

	//Width and Height of tiles/player (except for heightBig which is double the size)
	private static final int width = 32, height = width;
	
	//Fonts
	public static Font fontAmaticBold40, fontJackPortRegular40, fontRobotoRegular40
	                 , fontAmaticBold30, fontJackPortRegular30, fontRobotoRegular30
	                 , fontAmaticBold20, fontJackPortRegular20, fontRobotoRegular20;

	//Holds all tiles/items/etc.
	public static BufferedImage player, ground, sky, rainbowSky
							  , groundBevel, skyBevel //Bevels
							  , red, orange, yellow, yellowGreen, green, cyan, blueLight, blueDark, purple, purplePink, pink, magenta, white, greyLight, greyDark, black //Colors
	                          , notFound; //Handles when a tile cannot be found
	
	//Holds all static entities
	public static BufferedImage obj1, obj2;

	//Holds all static flash entities
	public static BufferedImage fobj1, fobj2;

	//Holds all item drops
	public static BufferedImage item1, item2;

//player Animations
	public static BufferedImage[] player_right, player_left, player_jump, player_crouch; 		

	//start game button
	public static BufferedImage[] btn_start;
	
	//inventory screen
	public static BufferedImage inventoryScreen;
	
	//Initializes Assets
	public static void init() {
		
		//Set font(s)
		fontAmaticBold40 = FontLoader.loadFont("res/fonts/amatic/Amatic-Bold.ttf", 40);
		fontJackPortRegular40 = FontLoader.loadFont("res/fonts/JACKPORT REGULAR NCV.ttf", 40);
		fontRobotoRegular40 = FontLoader.loadFont("res/fonts/Roboto/Roboto-Black.ttf", 40);
		fontAmaticBold30 = FontLoader.loadFont("res/fonts/amatic/Amatic-Bold.ttf", 30);
		fontJackPortRegular30 = FontLoader.loadFont("res/fonts/JACKPORT REGULAR NCV.ttf", 30);
		fontRobotoRegular30 = FontLoader.loadFont("res/fonts/Roboto/Roboto-Black.ttf", 30);
		fontAmaticBold20 = FontLoader.loadFont("res/fonts/amatic/Amatic-Bold.ttf", 20);
		fontJackPortRegular20 = FontLoader.loadFont("res/fonts/JACKPORT REGULAR NCV.ttf", 20);
		fontRobotoRegular20 = FontLoader.loadFont("res/fonts/Roboto/Roboto-Black.ttf", 20);
		
		//Loads tiles, items, and player texture file
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
	
		//Inventory Screen
		inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
	
		//Start game buttons
		btn_start = new BufferedImage[2];
			btn_start[0] = sheet.crop(width * 6, height * 4, width * 2, height);
			btn_start[1] = sheet.crop(width * 6, height * 5, width * 2, height);
		
		//Static player
		player		= sheet.crop(width * 1, height * 4, width, height);

//		//Player Right
//		player_right = new BufferedImage[2];
//			player_right[0] = sheet.crop(width * 4, height * 1, width, height);
//			player_right[1] = sheet.crop(width * 5, height * 1, width, height);
//		//Player Left
//		player_left = new BufferedImage[2];
//			player_left[0] = sheet.crop(width * 6, height * 1, width, height);
//			player_left[1] = sheet.crop(width * 7, height * 1, width, height);
//		//Player Jump
//		player_jump = new BufferedImage[2];
//			player_jump[0] = sheet.crop(width * 4, height * 0, width, height);
//			player_jump[1] = sheet.crop(width * 5, height * 0, width, height);
//		//Player Crouch
//		player_crouch = new BufferedImage[2];
//			player_crouch[0] = sheet.crop(width * 6, height * 0, width, height);
//			player_crouch[1] = sheet.crop(width * 7, height * 0, width, height);
		
	/*TILES*/
		groundBevel	= sheet.crop(width * 5, height * 6, width, height);
		skyBevel	= sheet.crop(width * 5, height * 5, width, height);
		ground		= sheet.crop(width * 7, height * 7, width, height);
		sky			= sheet.crop(width * 6, height * 6, width, height);
		rainbowSky 	= sheet.crop(width * 7, height * 6, width, height);

	/*COLORS*/
		red 		= sheet.crop(width * 1, height * 4, width, height);
		orange 		= sheet.crop(width * 2, height * 4, width, height);
		yellow 		= sheet.crop(width * 3, height * 4, width, height);
		yellowGreen	= sheet.crop(width * 4, height * 4, width, height);
		green 		= sheet.crop(width * 1, height * 5, width, height);
		cyan 		= sheet.crop(width * 2, height * 5, width, height);
		blueLight 	= sheet.crop(width * 3, height * 5, width, height);
		blueDark 	= sheet.crop(width * 4, height * 5, width, height);
		purple 		= sheet.crop(width * 1, height * 6, width, height);
		purplePink 	= sheet.crop(width * 2, height * 6, width, height);
		pink 		= sheet.crop(width * 3, height * 6, width, height);
		magenta 	= sheet.crop(width * 4, height * 6, width, height);
		white 		= sheet.crop(width * 1, height * 7, width, height);
		greyLight 	= sheet.crop(width * 2, height * 7, width, height);
		greyDark 	= sheet.crop(width * 3, height * 7, width, height);
		black 		= sheet.crop(width * 4, height * 7, width, height);
		notFound= sheet.crop(width * 7, height * 7, width, height);
		
		//Static Entities
		obj1 	= sheet.crop(width * 2, height * 4, width, height);
		obj2 	= sheet.crop(width * 4, height * 5, width, height);

		//Static Flash Entities
		fobj1 	= sheet.crop(width * 3, height * 4, width, height);
		fobj2 	= sheet.crop(width * 3, height * 5, width, height);

		//Item Drop Entities
		item1 	= sheet.crop(width * 4, height * 6, width, height);
		item2 	= sheet.crop(width * 3, height * 6, width, height);
	}

}
