package dev.apauley.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dev.apauley.entities.Entity;
import dev.apauley.general.Handler;
import dev.apauley.gfx.Animation;
import dev.apauley.gfx.Assets;
import dev.apauley.inventory.Inventory;

/*
 * The player that our users will control.
 */

public class Player extends Creature{

	//Animations
	private Animation animJump, animCrouch, animRight, animLeft;
	private int animSpeed = 500;
	
	//Attack Timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
	
	//Player Inventory
	private Inventory inventory;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT,"PLAYER");

		//Boundary box for player
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = Creature.DEFAULT_CREATURE_WIDTH - 2;
		bounds.height = Creature.DEFAULT_CREATURE_HEIGHT - 2;
		
		//Animations
//		animJump 	= new Animation(animSpeed, Assets.player_jump);
//		animCrouch 	= new Animation(animSpeed, Assets.player_crouch);
//		animRight 	= new Animation(animSpeed, Assets.player_right);
//		animLeft 	= new Animation(animSpeed, Assets.player_left);
		
		//Inventory
		inventory = new Inventory(handler);
	}

	@Override
	public void tick() {
		
		//Animations
//		animJump.tick();
//		animCrouch.tick();
//		animRight.tick();
//		animLeft.tick();

		//Gets movement using speed
		getInput();		

		//Sets position using movement
		move();
		
		//Centers camera on player
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		checkAttacks();
		
		//Inventory
		inventory.tick();
	}
	
	//Check is user is pressing attacking key. If so, generate attack
	public void checkAttacks() {
		
		//only allow attacks on Phase > x
		if(handler.getPhaseManager().getCurrentPhase() < 5)
			return;		
		
		//Update AttackTimer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		//check if can attack yet
		if(attackTimer < attackCooldown)
			return;
		
		//Player cannot attack while in inventory
		if(inventory.isActive())
			return;
		
		//collision bounds rectangle (Gets coordinates for collision box of player)
		Rectangle cb = getCollisionBounds(0, 0);
		
		//attack rectangle
		Rectangle ar = new Rectangle();
		
		//How close the player needs to be to hit target
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp) {
			ar.x = cb.x + cb.width / 2 - arSize / 2; //Could just do cb.x BUT if you ever want attack that is a different size, this code is more flexible to always be in the right place
			ar.y = cb.y + - arSize;
		}else if(handler.getKeyManager().aDown) {
			ar.x = cb.x + cb.width / 2 - arSize / 2; //Could just do cb.x BUT if you ever want attack that is a different size, this code is more flexible to always be in the right place
			ar.y = cb.y + cb.height;
		}else if(handler.getKeyManager().aLeft) {
			ar.x = cb.x - arSize; 
			ar.y = cb.y + cb.height / 2 - arSize / 2; //Could just do cb.y BUT if you ever want attack that is a different size, this code is more flexible to always be in the right place
		}else if(handler.getKeyManager().aRight) {
			ar.x = cb.x + cb.width; 
			ar.y = cb.y + cb.height / 2 - arSize / 2; //Could just do cb.y BUT if you ever want attack that is a different size, this code is more flexible to always be in the right place
		} else { //If none of the above code happens, means we are not attacking, so exit method before executing subsequent code
			return;
		}
		
		//If Attacking, begin following code

		//For every entity
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			
			//We don't want to hurt ourselves, so if we loop to ourselves, just continue
			if(e.equals(this))
				continue;
			
			//If entity's collision bounds intersects with attack rectangle register a hit against entity
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);

				//Reset attackTimer
				attackTimer = 0;		
			
				return;
			}
		}
	}
		
	//The process that occurs when an entity dies
	@Override	
	public void die() {
		System.out.println("You Lose");
	}
		
	//Takes user input and performs various actions
	private void getInput() {

		//only allow movement on Phase > x
		if(handler.getPhaseManager().getCurrentPhase() < 5)
			return;		
		
		//Very important that every time we call this method we set xMove and yMove to 0
		xMove = 0;
		yMove = 0;
		
		//Player cannot move while in inventory
		if(inventory.isActive())
			return;
		
		//Setting x/y move to a certain speed, THEN moving player that much
		
		//Handles player Movement
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().right)
			xMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().jump)
			yMove += -30f;
		
		//If jump was just pressed, perform hangtime
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && !handler.getKeyManager().jump) {System.out.println("Both");}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
			System.out.println("Just Pressed");
			if(!handler.getKeyManager().jump) {
				System.out.println("Released Bbbbbbbbbbb");
				hangtime = true;
				hangTimeTimer = 0;	
			}
		}
			
	}

	@Override
	public void render(Graphics g) {

		if(handler.getPhaseManager().getCurrentPhase() > 5) {

			//Temporarily doing no animation:
			g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);	

			//Draw Player to screen WITH animation
			//g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);

		} else {

			//Draw Player to screen WITHOUT animation
			g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);			


		}

		//Debug Bounding Box:
		g.setColor(Color.YELLOW);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset())
				 , (int) (y + bounds.y - handler.getGameCamera().getyOffset())
				 , bounds.width, bounds.height);
		
	}
	
	//works just like inventory, but it renders things AFTER the other render, so that this is on top
	public void postRender(Graphics g) {

		//Inventory
		inventory.render(g);		
	}
	
	//Reset Player for phase changes and such
	public void resetPlayer() {
		setX(handler.getWorld().getSpawnX());
		setY(handler.getWorld().getSpawnY());
		setXMove(0);
		setYMove(0);
	}
	

	/*************** GETTERS and SETTERS ***************/

	//Gets current animation frame depending on movement/other
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			return animLeft.getCurrentFrame();
		} else  if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else  if (yMove < 0) {
			return animCrouch.getCurrentFrame();
		} else 
			return animJump.getCurrentFrame();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
