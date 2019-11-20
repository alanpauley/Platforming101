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

	//Collision with Tile Booleans
	protected boolean collisionWithTileTop, collisionWithTileBottom, collisionWithTileLeft, collisionWithTileRight;
	
	//Gravity on creatures
	protected final float DEFAULT_GRAVITY = 9.8f;
	
	//Tracks how much speed creature has
	protected float speed;
	
	//Tracks which direction the player is facing
	protected boolean faceTop, faceBottom, faceRight, faceLeft;

	//Helper for moving creatures on x and y plane
	protected float xMove, yMove;	
	
	//Tracks whether creature IS jumping (meaning still ascending
	protected boolean jumping;
	
	//Tracks creature jumping hangtime (whether should be floating after jumping)
	protected boolean hangtime;
		
	//Jump Timer
	protected long lastHangTimeTimer, hangTimeCooldown = 180, hangTimeTimer = hangTimeCooldown;
	
	//Creature Constructor. Establishes some defaults
	public Creature(Handler handler, float x, float y, int width, int height, float xMove, float yMove, String name) {
		super(handler, x,y, width, height, xMove, yMove, name);
		speed = DEFAULT_SPEED;
		this.xMove = xMove;
		this.yMove = yMove;

	}

	//Gravity on creatures
	public void gravity() {
		
		//Update HangTimeTimer
		hangTimeTimer += System.currentTimeMillis() - lastHangTimeTimer;
		lastHangTimeTimer = System.currentTimeMillis();
		
		//check if can ready to fall yet yet
		if(hangTimeTimer < hangTimeCooldown)
		return;

		//Reset hangTimeTimer
		hangtime = false;

		//only allow gravity on Phase >= 3
		if(handler.getPhaseManager().getCurrentPhase() < 3) {
			yMove = 0;
			return;		
		}

		//if player is jumping and not in hangtime, apply gravity
		if(yMove <= 0 && !hangtime) {
			yMove += DEFAULT_GRAVITY;
		}
	}
	
	//Moves creature using helpers
	public void move() {
		
		//Check all collisions
		/*X coordinate of creature, + where you want to move to, + x bound offset, 
		 * + bounds width since moving right and checking right side
		 */

		//Right Collision Check
		int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
		if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
			collisionWithTileRight = false;
		else
			collisionWithTileRight = true;
		
		//Left Collision Check
		tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
		if(!collisionWithTile(tx, (int) (y + bounds.y)/ Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
			collisionWithTileLeft = false;
		else
			collisionWithTileLeft = true;

		//Top Collision Check
		int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
		if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
			collisionWithTileTop = false;
		else
			collisionWithTileTop = true;
		
		//Bottom Collision Check
		ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
		if(!collisionWithTile((int) (x + bounds.x)/ Tile.TILEWIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
			collisionWithTileBottom = false;
		else
			collisionWithTileBottom = true;
		
		
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

			//Set facing right = true
			setFaceRight(true);
			
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			/*Check the tile upper right is moving in to, and lower right is moving in to
			 * If both tiles are NOT solid (thus ! in front of collision method), then go ahead and move!
			 */
			if(!collisionWithTileRight)
				x += xMove;
			else
				//move player as close to the tile as possible without being inside of it
				//Note: We add a 1-pixel gap which allows the player to "slide" and not get stuck along the boundaries
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			
		//Moving left
		}else if(xMove < 0) {

			//Set facing left = true
			setFaceLeft(true);
			
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			//Same check
			if(!collisionWithTileLeft)
				x += xMove;
			else
				//move player as close to the tile as possible without being inside of it
				//Note: We weirdly don't have to add a 1-pixel gap for "sliding" to not get stuck along the boundaries. Don't ask me why...
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
		} 
			
	}
	
	public void moveY() {
		
		//Moving up
		if(yMove < 0) {
			
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if(!collisionWithTileTop)
				y += yMove;
			else
				//move player as close to the tile as possible without being inside of it
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			
		//Moving down
		}else if(yMove > 0) {
			
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			
			if(!collisionWithTileBottom)
				y += yMove;
			else
				//move player as close to the tile as possible without being inside of it
				//Note: We add a 1-pixel gap which allows the player to "slide" and not get stuck along the boundaries
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
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
	public void setXMove(float xMove) {
		this.xMove = xMove;
	}

	//Gets creature yMovement
	public float getyMove() {
		return yMove;
	}

	//Sets creature yMovement
	public void setYMove(float yMove) {
		this.yMove = yMove;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isHangtime() {
		return hangtime;
	}

	public void setHangtime(boolean hangtime) {
		this.hangtime = hangtime;
	}

	//Checks whether Creature is colliding with Tile on Top
	public boolean isCollisionWithTileTop() {
		return collisionWithTileTop;
	}

	//Set whether Creature is colliding with Tile on Top
	public void setCollisionWithTileTop(boolean collisionWithTileTop) {
		this.collisionWithTileTop = collisionWithTileTop;
	}

	//Checks whether Creature is colliding with Tile on Bottom
	public boolean isCollisionWithTileBottom() {
		return collisionWithTileBottom;
	}

	//Set whether Creature is colliding with Tile on Bottom
	public void setCollisionWithTileBottom(boolean collisionWithTileBottom) {
		this.collisionWithTileBottom = collisionWithTileBottom;
	}
	
	//Checks whether Creature is colliding with Tile on Left
	public boolean isCollisionWithTileLeft() {
		return collisionWithTileLeft;
	}

	//Set whether Creature is colliding with Tile on Left
	public void setCollisionWithTileLeft(boolean collisionWithTileLeft) {
		this.collisionWithTileLeft = collisionWithTileLeft;
	}
	
	//Checks whether Creature is colliding with Tile on Right
	public boolean isCollisionWithTileRight() {
		return collisionWithTileRight;
	}

	//Set whether Creature is colliding with Tile on Right
	public void setCollisionWithTileRight(boolean collisionWithTileRight) {
		this.collisionWithTileRight = collisionWithTileRight;
	}

	//Checks whether player is facing up
	public boolean isFaceTop() {
		return faceTop;
	}

	//Sets whether player is facing up
	public void setFaceTop(boolean faceTop) {
		this.faceTop = faceTop;
	}

	//Checks whether player is facing down
	public boolean isFaceBottom() {
		return faceBottom;
	}

	//Sets whether player is facing down
	public void setFaceBottom(boolean faceBottom) {
		this.faceBottom = faceBottom;
	}

	//Checks whether player is facing right
	public boolean isFaceRight() {
		return faceRight;
	}

	//Sets whether player is facing right
	public void setFaceRight(boolean faceRight) {
		this.faceRight = faceRight;
	}

	//Checks whether player is facing left
	public boolean isFaceLeft() {
		return faceLeft;
	}

	//Sets whether player is facing left
	public void setFaceLeft(boolean faceLeft) {
		this.faceLeft = faceLeft;
	}

}
