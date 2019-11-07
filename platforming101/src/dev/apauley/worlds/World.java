package dev.apauley.worlds;

import java.awt.Graphics;

import dev.apauley.general.Game;
import dev.apauley.tiles.Tile;
import dev.apauley.utilities.Utilities;

/*
 * Loads levels from text files and renders them to the screen
 */

public class World {

	private Game game;
	
	//Width and Height of level
	private int width, height;
	
	//X and Y Position the player will spawn at
	private int spawnX, spawnY;

	//will store tile id's in a x by y multidimensional array
	private int[][] tiles;
	
	//Constructor
	public World(Game game, String path) {
		this.game = game;
		loadWorld(path);
	}

	public void tick() {

	}
	
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(g, (int) (x * Tile.TILEWIDTH - game.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - game.getGameCamera().getyOffset()));
			}
		}		
	}

	//Gets data from txt file and stores in tiles multidimensional array
	public void loadWorld(String path) {
		
		//Holds txt level path
		String file = Utilities.loadFileAsString(path);
		
		//Split all characters from input file using spaces/new line character ("\\s+")
		String[] tokens = file.split("\\s+");
		
		//Width of Level
		width = Utilities.parseInt(tokens[0]);

		//Height of Level
		height = Utilities.parseInt(tokens[1]);
		
		//Player Spawn X Position
		spawnX = Utilities.parseInt(tokens[2]);

		//Player Spawn X Position
		spawnY = Utilities.parseInt(tokens[3]);
		
		//Creates tiles multidimensional array based on width and height
		tiles = new int[width][height];
		
		//Loops through width and height and adds tiles to multidimensional array
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {

				//Need to do +4 because we are setting first 4 variables above from level file, so skip those when doing tiles
				tiles[x][y] = Utilities.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}

	/*************** GETTERS and SETTERS ***************/

	//Takes tile array and indexes at whatever tile is in the tile array at each x and y position
	public Tile getTile(int x, int y) {
		/*In case player somehow gets outside of game/level boundaries - i.e. a glitch - 
			do this check and return a normal tile so game doesn't crash*/
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grass;
		
		Tile t = Tile.tiles[tiles[x][y]];
		
		//If cannot find a result, return missingTile to point out that there is an issue
		if(t == null) 
			return Tile.dirt;
		
		//Returns tile as x|y index
		return t;
	}
}
