package dev.apauley.worlds;

import java.awt.Graphics;

import dev.apauley.entities.EntityManager;
import dev.apauley.entities.creatures.Player;
import dev.apauley.entities.statics.Rock;
import dev.apauley.entities.statics.Tree;
import dev.apauley.general.Handler;
import dev.apauley.items.ItemManager;
import dev.apauley.tiles.Tile;
import dev.apauley.utilities.Utilities;

/*
 * Loads levels from text files and renders them to the screen
 */

public class World {

	//Main Handler object (which can reference game)
	private Handler handler;
	
	//Width and Height of level
	private int width, height;
	
	//X and Y Position the player will spawn at
	private int spawnX, spawnY;

	//will store tile id's in a x by y multidimensional array
	private int[][] tiles;
	
	//stores Entities
	private EntityManager entityManager;
	
	//stores Items
	private ItemManager itemManager;
	
	//Constructor
	public World(Handler handler, String path) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 300, 400));
		itemManager = new ItemManager(handler);

		//Adds entities to the Entity list
		entityManager.addEntity(new Tree(handler, 200,200));
		entityManager.addEntity(new Rock(handler, 180,350));
		entityManager.addEntity(new Tree(handler, 400,210));
		entityManager.addEntity(new Rock(handler, 380,360));
		entityManager.addEntity(new Tree(handler, 600,210));
		entityManager.addEntity(new Rock(handler, 580,370));
		
		//Loads world via file
		loadWorld(path);
		
		//Sets the player spawn position based on the spawn position from the world.txt file
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}

	public void tick() {
		
		//Items
		itemManager.tick();

		//Entities
		entityManager.tick();

	}
	
	public void render(Graphics g) {

		/*
		 * Below for loops used to go through all tiles, starting with 0, end with height/width
		 * Now will loop only through VISIBLE tiles, as denoted by these Start/End variables
		 * 
		 * Uses max method to ensure we never start with a negative. And offset divided by Tile width/height
		 *      to convert from pixels to actual tile number
		 *      
		 * Uses min for end, whether we reach end of map (width/height) or not there yet (game camera)
		 */
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / (Tile.TILEWIDTH)) ;
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / (Tile.TILEWIDTH) +2);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / (Tile.TILEHEIGHT)) ;
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / (Tile.TILEHEIGHT) +2);

		//Loops through level text file and populates all tiles (inefficient until we are only rendering what is on screen) 
		//Note: Start with y for loop first because it can prevent issues (he didn't explain why)
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(x,y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}		
		
		//Items
		itemManager.render(g);

		//Entities
		entityManager.render(g);
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

	//Gets Level Width
	public int getWidth() {
		return width;
	}
	
	//Gets the EntityManager
	public EntityManager getEntityManager() {
		return entityManager;
	}

	//Gets Level Height
	public int getHeight() {
		return height;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

}
