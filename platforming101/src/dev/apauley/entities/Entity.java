package dev.apauley.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.apauley.general.Handler;

/*
 * The base shell for all Entities in game
 */
public abstract class Entity {

	//Default Creature Values
	public static final int DEFAULT_HEALTH = 3;
	public static final int DEFAULT_FLASH = 7;

	//Main Handler object (can reference game or anything from here)
	protected Handler handler;
	
	//Float = smoother movement using decimals for calculations
	//X and Y coordinates of entity
	protected float x, y;
	
	//x and y Move
	protected float xMove, yMove;

	//Size of entity
	protected int width, height;
	
	//Tracks how much speed creature has
	protected float speed;
	
	//Collision with Tile Booleans
	protected boolean collisionWithTileTop, collisionWithTileBottom, collisionWithTileLeft, collisionWithTileRight;
	
	//Tracks which direction the player is facing
	protected boolean faceTop, faceBottom, faceRight, faceLeft;

	//Tracks how much HP creature has
	protected int health;

	//Tracks whether target is hit
	protected int flash;
	
	//Tracks whether entity is still alive/active
	protected boolean active = true;

	//Boundary box for collision detection
	protected Rectangle bounds;
	
	//Contains entity Full name (ie, PLAYER1, ENEMY3, ENEMY3BULLET2, etc.)
	protected String fullName;

	//Contains entity name (Player, enemy, bullet, etc.)
	protected String name;

	//Contains entity group (ie, player, enemy, etc.)
	protected String group;
	
	//Contains entity id (1,2,3) used to append per group (ie: PLAYER1, ENEMY1, ENEMY1BULLET2, etc.)
	protected int id;
	
	//Constructor to set Defaults
	public Entity(Handler handler, float x, float y, int width, int height, float xMove, float yMove, String name, String group) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.group = group;
		health = DEFAULT_HEALTH;
		flash = 0;
		this.xMove = xMove;
		this.yMove = yMove;
				
		//By default, set boundary box to be exact size of entity
		//So 0,0 to start top left of entity, then same width/height
		bounds = new Rectangle(0, 0, width, height);			
	}
	
	//Updates Entity
	public abstract void tick();
	
	//Draws Entity
	public abstract void render(Graphics g);
	
	//The process that occurs when an entity dies
	public abstract void die();
	
	//subtract health from entity and flash them
	public void hurt(int amt) {
		health -= amt;
		
		flash = DEFAULT_FLASH;
		
		//If entity loses all health, set active to false to remove from game
		if(health <= 0) {
			setActive(false);
			die();
		}
	}

	//if entity is flashing, decrement
	public void flash() {
		
		if(flash > 0)
			flash--;
	}

	/*************** HELPER METHODS ***************/
	
	//Checks all collisions in game
	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {

			//We don't want to check against ourself
			if(e.equals(this))
				continue;
			
			//We don't want players hurting players or enemies hurting enemies, so this may stops friendly fire
			if(e.group.equals(group)) {
				continue;
			}
			
			//We don't want bullets/enemies cancelling each other out
			if(e.name.equals(name))
				continue;
			
			//if intersects, collision = detected
			if(e.getCollisionBounds(0f,0f).intersects(getCollisionBounds(xOffset, yOffset))) {

				//We don't want players hurting enemies by touching
				if(!e.name.equals("BULLET") && !name.equals("BULLET")) {
//					if(e.name.equals("ENEMY")) {
//						e.x -= 3;
//						System.out.println("test1");
//					}
//					if(name.equals("ENEMY")) {
//						x += 3;
//						System.out.println("test2");
//					}
					continue;
				}
				else {
					//System.out.println("[" + e.group + "] " + e.fullName + " >> HURT << " + fullName + " [" + group + "]");
					//die();
					return true;
				}
			}
		}
		
		//Otherwise, no collision
		return false;
	}
	
	//Gets the bounds (collision) around entity
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	/*************** GETTERS and SETTERS ***************/

	//Gets x Position
	public float getX() {
		return x;
	}

	//Sets x Position
	public void setX(float x) {
		this.x = x;
	}

	//Gets y Position
	public float getY() {
		return y;
	}

	//Sets y Position
	public void setY(float y) {
		this.y = y;
	}

	//Gets entity width
	public int getWidth() {
		return width;
	}

	//Sets entity width
	public void setWidth(int width) {
		this.width = width;
	}

	//Gets entity height
	public int getHeight() {
		return height;
	}

	//Sets entity height
	public void setHeight(int height) {
		this.height = height;
	}

	//Gets entity name
	public String getName() {
		return name;
	}

	//Sets entity name
	public void setName(String name) {
		this.name = name;
	}

	//Gets entity health
	public int getHealth() {
		return health;
	}

	//Sets entity health
	public void setHealth(int health) {
		this.health = health;
	}

	//Gets whether entity is active or not
	public boolean isActive() {
		return active;
	}

	//Sets whether entity is active or not
	public void setActive(boolean active) {
		this.active = active;

		//If player is active, exit procedure
		if(active)
			return;
		
		//Decrement counts accordingly:
		if(group.equals("PLAYER")) {
			if(name.equals("PLAYER"))
				handler.getWorld().getEntityManager().setPlayerCount(handler.getWorld().getEntityManager().getPlayerCount() - 1);
			if(name.equals("BULLET"))
				handler.getWorld().getEntityManager().setBulletPlayerCount(handler.getWorld().getEntityManager().getBulletPlayerCount() - 1);
		}
		if(group.equals("ENEMY")) {
			if(name.equals("ENEMY"))
				handler.getWorld().getEntityManager().setEnemyCount(handler.getWorld().getEntityManager().getEnemyCount() - 1);
			if(name.equals("BULLET")) {
				handler.getWorld().getEntityManager().setBulletEnemyCount(handler.getWorld().getEntityManager().getBulletEnemyCount() - 1);
			}
		}

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
