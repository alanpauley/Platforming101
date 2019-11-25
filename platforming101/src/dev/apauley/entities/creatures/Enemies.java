package dev.apauley.entities.creatures;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;

/*
 * Enemies
 */

public class Enemies extends Creature{

	//Waits til player is loaded before finishing initialization
	protected boolean initialized;
	
	//Attack Timer
	private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
	
	public Enemies(Handler handler, float x, float y, float xMove, float yMove) {
		super(handler, x, y, handler.getGVar().get_DEFAULT_CREATURE_WIDTH(), handler.getGVar().get_DEFAULT_CREATURE_HEIGHT(), xMove, yMove, "ENEMY", "ENEMY");
		
		//Boundary box for player
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = handler.getGVar().get_DEFAULT_CREATURE_WIDTH() - 2;
		bounds.height = handler.getGVar().get_DEFAULT_CREATURE_HEIGHT() - 2;
		
		this.group = group;
		
		//Rename enemy with number appended based on enemyCount
		id = handler.getWorld().getEntityManager().getEnemyCount();
		fullName = name + id;			

		//Increment enemy count
		handler.getWorld().getEntityManager().setEnemyCount(handler.getWorld().getEntityManager().getEnemyCount() + 1);
	}
	
	@Override
	public void tick() {
		super.tick();

		if(!active)
			return;
		
		//Waits til player is loaded before finishing initialization
		if(handler.getWorld().getEntityManager().getPlayer().getX() == 0)
			return;
		else if(!initialized) {
			initialized = true;

			//If enemy is to the left of player, set them facing right (towards player) 
			if(x < handler.getWorld().getEntityManager().getPlayer().getX())
				faceRight = true;
			
			//else face the other direction (towards player)
			else
				faceLeft = true;			
		}
		
		//if enemy's x/y distance away from player is too great, remove enemy
		if(Math.abs(x - handler.getWorld().getEntityManager().getPlayer().getX()) > 2000 || Math.abs(y - handler.getWorld().getEntityManager().getPlayer().getY()) > 2000)
			setActive(false);
		
		//Block for phase to fall through ground
		if(handler.getPhaseManager().getCurrentPhase() < 11) {
			y += handler.getGVar().get_DEFAULT_GRAVITY();
			return;
		} 

		//Gravity's effect on the enemy
		gravity();

		//Handles vertical movement for enemies
		move();

		//Block for phase to collide with other entities
		if(handler.getPhaseManager().getCurrentPhase() > 11) {
			//Handles enemy movement
			enemyMove();
		}
		
		//Block for phase to have enemy shoot
		if(handler.getPhaseManager().getCurrentPhase() > 13.) {
			enemyShoot();
		}		
	}
	
	@Override
	public void render(Graphics g) {

		//Flash player if hit
		if(handler.getPhaseManager().getCurrentPhase() > 12) {

			if(flash > 0)
				g.drawImage(Assets.white, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);
			else
				g.drawImage(Assets.yellowGreen, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);

		} else {
			g.drawImage(Assets.yellowGreen, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
			
	}

	@Override
	public void die() {
		System.out.println(fullName + ": Dead");
		setActive(false);
	}
	
	//Handles enemy movement
	public void enemyMove() {
		
		//If enemy isn't touching ground, no movement.
		if(!collisionWithTileBottom)
			xMove = 0f;
		
		//Else, movement in direction they are facing
		else {
			if(faceRight)
				xMove = speed; 
			if(faceLeft)
				xMove = -speed; 
		}

		if(collisionWithTileLeft) {
			faceRight = true;
			faceLeft = false;
			xMove *= -1;			
		}
		if(collisionWithTileRight) {
			faceRight = false;
			faceLeft = true;
			xMove *= -1;			
		}
	}
	
	//Handles enemy shooting
	public void enemyShoot() {
		
		//Update AttackTimer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		//check if can attack yet
		if(attackTimer < attackCooldown)
			return;
		
		//Check if enemy is facing player
		if(x - handler.getWorld().getEntityManager().getPlayer().getX() > 0 && faceRight) // If on the right and facing right, exit
			return;
		if(x - handler.getWorld().getEntityManager().getPlayer().getX() < 0 && faceLeft) // If on the left and facing left, exit
			return;
		
		float xMove = 10f;
		float yMove = 0f;
		
		//Get enemy direction(s) to get yMoves of bullets (don't need left and right since that's implied in mouse click side)
		if(faceTop)
			yMove = -10f;
		if(faceBottom)
			yMove = 10f;

		//if enemy sees player, shoot bullet at player
		if(Math.abs(x - handler.getWorld().getEntityManager().getPlayer().getX()) <= 1000 && Math.abs(y - handler.getWorld().getEntityManager().getPlayer().getY()) <= handler.getWorld().getEntityManager().getPlayer().getHeight()/4) {

			//Different bullet direction depending on where the player is in relation to the enemy
			if(faceRight) {
				xMove = 10f;
				handler.getWorld().getEntityManager().getEntitiesLimbo().add(new Bullet(handler, x + width + 10
						  , y + height / 2 - Assets.obj1.getHeight()/3, xMove, yMove, Assets.purplePink, "BULLET", "ENEMY", id));
			} 
			if(faceLeft) {
				xMove = -10f;
				handler.getWorld().getEntityManager().getEntitiesLimbo().add(new Bullet(handler, x - Assets.obj1.getWidth()
						  , y + height / 2 - Assets.obj1.getHeight()/3, xMove, yMove, Assets.purplePink, "BULLET", "ENEMY", id));
			}			

			//Reset attackTimer
			attackTimer = 0;		

		}
		
	}
	
	
}