package dev.apauley.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.apauley.tiles.tileAssets.Blue;
import dev.apauley.tiles.tileAssets.Grey;
import dev.apauley.tiles.tileAssets.Ground;
import dev.apauley.tiles.tileAssets.GroundBevel;
import dev.apauley.tiles.tileAssets.NotFound;
import dev.apauley.tiles.tileAssets.RainbowSky;
import dev.apauley.tiles.tileAssets.Sky;
import dev.apauley.tiles.tileAssets.SkyBevel;

/*
 * Contains Everything that every tile must have
 */

public class Tile {

	/*************STATIC VARIABLES*****************/	

	//Holds all tile references
	public static Tile[] tiles = new Tile[1000];

	//Initializes Tiles with id values
	public static Tile blue 			= new Blue(000);
	public static Tile grey 			= new Grey(001);
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
