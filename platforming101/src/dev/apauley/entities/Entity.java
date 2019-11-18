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

	//Size of entity
	protected int width, height;
	
	//Tracks how much HP creature has
	protected int health;

	//Tracks whether target is hit
	protected int flash;
	
	//Tracks whether entity is still alive/active
	protected boolean active = true;

	//Boundary box for collision detection
	protected Rectangle bounds;
	
	//Contains entity name
	protected String name;

	//Constructor to set Defaults
	public Entity(Handler handler, float x, float y, int width, int height, String name) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		health = DEFAULT_HEALTH;
		flash = 0;
				
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
			active = false;
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
			
			//if intersects, collision = detected
			if(e.getCollisionBounds(0f,0f).intersects(getCollisionBounds(xOffset, yOffset))) {
				return true;
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
	}

}
