package dev.apauley.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.apauley.tiles.tileAssets.Ground;
import dev.apauley.tiles.tileAssets.GroundBevel;
import dev.apauley.tiles.tileAssets.NotFound;
import dev.apauley.tiles.tileAssets.RainbowSky;
import dev.apauley.tiles.tileAssets.Sky;
import dev.apauley.tiles.tileAssets.SkyBevel;
import dev.apauley.tiles.tileAssets.colors.Black;
import dev.apauley.tiles.tileAssets.colors.BlueDark;
import dev.apauley.tiles.tileAssets.colors.BlueLight;
import dev.apauley.tiles.tileAssets.colors.Cyan;
import dev.apauley.tiles.tileAssets.colors.Green;
import dev.apauley.tiles.tileAssets.colors.GreyDark;
import dev.apauley.tiles.tileAssets.colors.GreyLight;
import dev.apauley.tiles.tileAssets.colors.Magenta;
import dev.apauley.tiles.tileAssets.colors.Orange;
import dev.apauley.tiles.tileAssets.colors.Pink;
import dev.apauley.tiles.tileAssets.colors.Purple;
import dev.apauley.tiles.tileAssets.colors.PurplePink;
import dev.apauley.tiles.tileAssets.colors.Red;
import dev.apauley.tiles.tileAssets.colors.White;
import dev.apauley.tiles.tileAssets.colors.Yellow;
import dev.apauley.tiles.tileAssets.colors.YellowGreen;

/*
 * Contains Everything that every tile must have
 */

public class Tile {

	/*************STATIC VARIABLES*****************/	

	//Holds all tile references
	public static Tile[] tiles = new Tile[1000];

	//Initializes Tiles with id values
	
	/*COLORS*/
	public static Tile red 				= new Red(0);
	public static Tile orange 			= new Orange(1);
	public static Tile yellow 			= new Yellow(2);
	public static Tile yellowGreen 		= new YellowGreen(3);
	public static Tile green 			= new Green(4);
	public static Tile cyan 			= new Cyan(5);
	public static Tile blueLight 		= new BlueLight(6);
	public static Tile blueDark 		= new BlueDark(7);
	public static Tile purple 			= new Purple(8);
	public static Tile purplePink 		= new PurplePink(9);
	public static Tile pink 			= new Pink(10);
	public static Tile magenta 			= new Magenta(11);
	public static Tile white 			= new White(12);
	public static Tile greyLight 		= new GreyLight(13);
	public static Tile greyDark 		= new GreyDark(14);
	public static Tile black 			= new Black(15);

	/*TILES*/
	public static Tile skyBevel			= new SkyBevel(100);
	public static Tile grounddBevel 	= new GroundBevel(101);
	public static Tile ground 			= new Sky(200);
	public static Tile sky 				= new Ground(201);
	public static Tile rainbowSky 		= new RainbowSky(300);
	public static Tile notFound			= new NotFound(999);
	
	/*************CLASS VARIABLES*****************/	

	//Default Tile size
	public static final int TILEWIDTH = 64
			              , TILEHEIGHT = TILEWIDTH;

	protected BufferedImage texture;

	//Every id should be unique, so final
	protected final int id;

	//Tile Constructor
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		//Sets tiles array at index (id) equal to current Tile being constructed
		tiles[id] = this;
	}

	//Unused tick() Method
	public void tick() {}

	//draws tile at x & y position with proper size using final variables and multiplier
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture,  x,  y, TILEWIDTH, TILEHEIGHT, null);
	}

	/*************** GETTERS and SETTERS ***************/

	//Gets Tile id
	public int getId() {
		return id;
	}
	
	//Gets whether tile is Solid or not
	public boolean isSolid() {
		return false;
	}
	
}
