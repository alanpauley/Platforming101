package dev.apauley.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.apauley.general.Handler;

/*
 * The base shell for all Entities in game
 */
public abstract class Entity {

	//Default Creature Values
	public int DEFAULT_HEALTH = 10;
	public int DEFAULT_FLASH = 50;
	public int DEFAULT_FLASH_HEALTH = 100;

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
	
	//Attack Timer
	protected long lastAttackTimer, attackCooldown, attackTimer;
	
	//Jump Timer
	protected long lastJumpTimer, jumpCooldown = 250, jumpTimer = jumpCooldown;
	
	//Tracks how many bullets entity has fired
	protected int bulletsFired = 0;
	
	//Collision with Tile Booleans
	protected boolean collisionWithTileTop, collisionWithTileBottom, collisionWithTileLeft, collisionWithTileRight;
	
	//Tracks which direction the player is facing
	protected boolean faceTop, faceBottom, faceRight, faceLeft;

	//Tracks how much HP creature has
	protected int health;

	//Tracks whether target is hit
	protected int flash;

	//Tracks whether target's hp is displayer
	protected int flashHealth;
	
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
	
	//Tracks various enemy stats/situations
	protected boolean fought;
	
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
		flashHealth = 0;
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
	public void hurt(int amt, Entity e) {

		int knockback = 40;
		
		//If hurt (for first time), assume fought and set to true; 
		if(!fought && name.equals("ENEMY")){
			fought = true;
			handler.getGame().getStatTracker().increaseEnemiesFought(1);
		}

		//If flashing, take no damage
		if(flash > 0)
			return;
		
		//if player, increment dmg taken and times hit
		if(name.equals("PLAYER")) {
			handler.getGame().getStatTracker().setHitCountPlayer(handler.getGame().getStatTracker().getHitCountPlayer() + 1);
			handler.getGame().getStatTracker().setHealthLostPlayer(handler.getGame().getStatTracker().getHealthLostPlayer() + amt);

		//if player, increment dmg taken and times hit
		} else if(name.equals("ENEMY")) {
			handler.getGame().getStatTracker().setHitCountEnemies(handler.getGame().getStatTracker().getHitCountEnemies() + 1);
			handler.getGame().getStatTracker().setHealthLostEnemies(handler.getGame().getStatTracker().getHealthLostEnemies() + amt);
		}
		
		//Player knockback scenarios
		if(e.x - this.x > 0)
			x -= knockback;
		if(e.x - this.x < 0)
			x += knockback;
		
		health -= amt;
		
		flash = DEFAULT_FLASH;
		flashHealth = DEFAULT_FLASH_HEALTH;
		
		//If entity loses all health, set active to false to remove from game
		if(health <= 0) {
			die();
		}
	}

	//if entity is flashing, decrement
	public void flash() {
		
		if(flash > 0)
			flash--;

		if(flashHealth > 0)
			flashHealth--;

	}

	/*************** HELPER METHODS ***************/
	
	//Checks all collisions in game
	public boolean checkEntityCollisions(float xOffset, float yOffset) {

		int amt = 1; //amount damaged (bullets hurt more than touching, etc.)
		
		//if game is paused, don't check these collisions
		if(handler.getGVar().getGSpeed() == 0)
			return false;
		
		//Phase check block to skip dmg
		if(handler.getPhaseManager().getCurrentPhase() < 13)
			return false;
		
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

				//touching = less dmg
				if(!e.name.equals("BULLET") && !name.equals("BULLET"))
					amt = 1;
				//bullets = more dmg
				else if(e.name.equals("BULLET") || name.equals("BULLET")) {
					amt = 2;
					if(e.name.equals("BULLET")) {
						if(e.group.equals("PLAYER")) //If player hit enemy with bullet, increase player hit with bullet counter
							handler.getGame().getStatTracker().setBulletsHitPlayer(handler.getGame().getStatTracker().getBulletsHitPlayer() + 1);
						else if(e.group.equals("ENEMY")) //else If enemy hit player with bullet, increase enemy hit with bullet counter
							handler.getGame().getStatTracker().setBulletsHitEnemies(handler.getGame().getStatTracker().getBulletsHitEnemies() + 1);
					}							
					if(name.equals("BULLET")) {
						if(group.equals("PLAYER")) //If player hit enemy with bullet, increase player hit with bullet counter
							handler.getGame().getStatTracker().setBulletsHitPlayer(handler.getGame().getStatTracker().getBulletsHitPlayer() + 1);
						else if(group.equals("ENEMY")) //else If enemy hit player with bullet, increase enemy hit with bullet counter
							handler.getGame().getStatTracker().setBulletsHitEnemies(handler.getGame().getStatTracker().getBulletsHitEnemies() + 1);
					}										
				}

				//Phase check for players to ONLY hurt enemies and vice versa by touching each other (not bullets)
				if(handler.getPhaseManager().getCurrentPhase() < 15) {
					if(!e.name.equals("BULLET") && !name.equals("BULLET")) {					
						e.hurt(amt, this);
						hurt(amt, e);
						return true;
					}
					else
						return false;
				}
					
					//We don't want players hurting enemies by touching
					if(!e.name.equals("BULLET") && !name.equals("BULLET")) {
						if(e.name.equals("PLAYER"))
							e.hurt(amt, this);
						if(name.equals("PLAYER"))
							hurt(amt, e);
						continue;
					}
	
				e.hurt(amt, this);
				hurt(amt, e);
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
				handler.getWorld().getEntityManager().decreaseEnemyCount(this, 1);
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
		
		//Only increment if not previously facing top
		if(!this.faceTop && faceTop && name.equals("PLAYER"))
			handler.getGame().getStatTracker().setFaceTopCountPlayer(handler.getGame().getStatTracker().getFaceTopCountPlayer() + 1);

		this.faceTop = faceTop;
	}

	//Checks whether player is facing down
	public boolean isFaceBottom() {
		return faceBottom;
	}

	//Sets whether player is facing down
	public void setFaceBottom(boolean faceBottom) {
		
		//Only increment if not previously facing bottom
		if(!this.faceBottom && faceBottom && name.equals("PLAYER"))
			handler.getGame().getStatTracker().setFaceBottomCountPlayer(handler.getGame().getStatTracker().getFaceBottomCountPlayer() + 1);

		this.faceBottom = faceBottom;
	}


	//Checks whether player is facing left
	public boolean isFaceLeft() {
		return faceLeft;
	}

	//Sets whether player is facing left
	public void setFaceLeft(boolean faceLeft) {
		
		//Only increment if not previously facing left
		if(!this.faceLeft && faceLeft && name.equals("PLAYER"))
			handler.getGame().getStatTracker().setFaceLeftCountPlayer(handler.getGame().getStatTracker().getFaceLeftCountPlayer() + 1);

		this.faceLeft = faceLeft;
	}

	//Checks whether player is facing right
	public boolean isFaceRight() {
		return faceRight;
	}

	//Sets whether player is facing right
	public void setFaceRight(boolean faceRight) {
		
		//Only increment if not previously facing right
		if(!this.faceRight && faceRight && name.equals("PLAYER"))
			handler.getGame().getStatTracker().setFaceRightCountPlayer(handler.getGame().getStatTracker().getFaceRightCountPlayer() + 1);
		this.faceRight = faceRight;
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public long getJumpCooldown() {
		return jumpCooldown;
	}

	public void setJumpCooldown(long jumpCooldown) {
		this.jumpCooldown = jumpCooldown;
	}

	public boolean isFought() {
		return fought;
	}

	public void setFought(boolean fought) {
		this.fought = fought;
	}
	
	public long getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(long attackCooldown) {
		this.attackCooldown = attackCooldown;
	}
	
	public void updateAttackCooldown() {
		if(group.equals("ENEMY"))
			attackCooldown = (long) (handler.getGVar().getCooldownDefault() * speed);
		//attackTimer = (long) (attackTimer * speed); //Not sure how to do this one properly
	}

	public int getBulletsFired() {
		return bulletsFired;
	}

	public void setBulletsFired(int bulletsFired) {
		this.bulletsFired = bulletsFired;
	}
}
