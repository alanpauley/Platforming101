package dev.apauley.worlds;

import java.awt.Graphics;

import dev.apauley.tiles.Tile;

/*
 * Loads levels from text files and renders them to the screen
 */

public class World {

	//Width and Height of level
	private int width, height;
	
	//will store tile id's in a x by y multidimensional array
	private int[][] tiles;
	
	//Constructor
	public World(String path) {
		loadWorld(path);
	}

	public void tick() {

	}
	
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
			}
		}		
	}

	//Gets data from txt file and stores in tiles multidimensional array
	public void loadWorld(String path) {
		width = 5;
		height = 5;
		tiles = new int[width][height];
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tiles[x][y] = 1;
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
