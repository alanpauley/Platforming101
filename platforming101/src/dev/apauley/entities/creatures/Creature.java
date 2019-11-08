package dev.apauley.entities.creatures;

import dev.apauley.entities.Entity;
import dev.apauley.general.Handler;
import dev.apauley.tiles.Tile;

/*
 * The base shell for all Creatures in game
 */
public abstract class Creature extends Entity {

	//Default Creature Values
	public static final int DEFAULT_HEALTH = 10;
	public static final float DEFAULT_SPEED = 3.6f;	
	public static final int DEFAULT_CREATURE_WIDTH = 64
			              , DEFAULT_CREATURE_HEIGHT = DEFAULT_CREATURE_WIDTH;

	//Tracks how much HP and speed creature has
	protected int health;
	protected float speed;

	//Helper for moving creatures on x and y plane
	protected float xMove, yMove;	
	
	//Creature Constructor. Establishes some defaults
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x,y, width, height);
		health = 10;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;

	}

	//Moves creature using helpers
	public void move() {
		moveX();
		moveY();		
	}

	//Instead of moving both x and y in same move method, creating separate
	//move methods for x and y
	public void moveX() {
		//Moving right
		if(xMove > 0) {
			
			/*Temp objects to hold what tile position would be if moved.. x, y upper, and y lower
			 * For example, starts as x coordinate then divide by tile width to determine Tile number/coordinate
			 * of tile we're trying to move in to
			 */
			
			/*X coordinate of creature, + where you want to move to, + x bound offset, 
			 * + bounds width since moving right and checking right side
			 */

			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			/*Check the tile upper right is moving in to, and lower right is moving in to
			 * If both tiles are NOT solid (thus ! in front of collision method), then go ahead and move!
			 */
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} 
		//Moving left
		}else if(xMove < 0) {

			//Same as above, except moving left, so don't need to add bounds width
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			//Same check
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} 
		}
	}
	
	public void moveY() {
		//Moving up
		if(yMove < 0) {
			
			//Same logic as xMove, but now for y. Using temp y, x left, and x right variables
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} 
		//Moving down
		}else if(yMove > 0) {
			
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			}
		}
	}
	
	//Takes in a tile array coordinate x/y and returns if that tile is solid
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x,y).isSolid();
	}

	/*************** GETTERS and SETTERS ***************/

	//Gets creature HP
	public int getHealth() {
		return health;
	}

	//Sets creature HP
	public void setHealth(int health) {
		this.health = health;
	}

	//Gets creature speed
	public float getSpeed() {
		return speed;
	}

	//Sets creature speed
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	//Gets creature xMovement
	public float getxMove() {
		return xMove;
	}

	//Sets creature xMovement
	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	//Gets creature yMovement
	public float getyMove() {
		return yMove;
	}

	//Sets creature yMovement
	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

}
