package dev.apauley.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.apauley.tiles.tileAssets.Dirt;
import dev.apauley.tiles.tileAssets.Grass;
import dev.apauley.tiles.tileAssets.Stone;

/*
 * Contains Everything that every tile must have
 */

public class Tile {

	/*************STATIC VARIABLES*****************/	

	//Holds all tile references
	public static Tile[] tiles = new Tile[256];

	//Initializes Tiles with id values
	public static Tile grass 	= new Grass(0);
	public static Tile dirt 	= new Dirt(1);
	public static Tile rock 	= new Stone(2);

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
