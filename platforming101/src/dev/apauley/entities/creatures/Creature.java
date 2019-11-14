package dev.apauley.entities.creatures;

import dev.apauley.entities.Entity;
import dev.apauley.general.Handler;
import dev.apauley.tiles.Tile;

/*
 * The base shell for all Creatures in game
 */
public abstract class Creature extends Entity {

	//Default Creature Values
	public static final int DEFAULT_CREATURE_WIDTH = 64
			              , DEFAULT_CREATURE_HEIGHT = DEFAULT_CREATURE_WIDTH;
	
	//Refactored speed to account for different sizes
	public static final float DEFAULT_SPEED = 3.0f + (DEFAULT_CREATURE_WIDTH / 64 * 1.25f); 

	//Gravity on creatures
	protected final float DEFAULT_GRAVITY = 9.8f;
	
	//Tracks how much speed creature has
	protected float speed;

	//Helper for moving creatures on x and y plane
	protected float xMove, yMove;	
	
	//Creature Constructor. Establishes some defaults
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x,y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;

	}

	//Gravity on creatures
	public void gravity() {
		
		//only allow gravity on Phase >= 3
		if(handler.getPhaseManager().getCurrentPhase() < 3) {
			yMove = 0;
			return;		
		}
								
		yMove = DEFAULT_GRAVITY;
	}
	
	//Moves creature using helpers
	public void move() {
		
		//If no collision, movement is allowed, otherwise stop
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();		
	}

	//Instead of moving both x and y in same move method, creating separate
	//move methods for x and y
	public void moveX() {
		//Moving right
		if(xMove > 0) {
			
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
			} else {
				//move player as close to the tile as possible without being inside of it
				//Note: We add a 1-pixel gap which allows the player to "slide" and not get stuck along the boundaries
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		//Moving left
		}else if(xMove < 0) {

			//Same as above, except moving left, so don't need to add bounds width
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			//Same check
			if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				//move player as close to the tile as possible without being inside of it
				//Note: We weirdly don't have to add a 1-pixel gap for "sliding" to not get stuck along the boundaries. Don't ask me why...
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
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
			} else {
				//move player as close to the tile as possible without being inside of it
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
			
		//Moving down
		}else if(yMove > 0) {
			
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				//move player as close to the tile as possible without being inside of it
				//Note: We add a 1-pixel gap which allows the player to "slide" and not get stuck along the boundaries
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	
	//Takes in a tile array coordinate x/y and returns if that tile is solid
	protected boolean collisionWithTile(int x, int y) {

		//only allow collisions on Phase >= 4
		if(handler.getPhaseManager().getCurrentPhase() < 4)
			return false;
								
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
