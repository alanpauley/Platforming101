package dev.apauley.entities.creatures;

import java.awt.Graphics;
import java.awt.Image;

import dev.apauley.entities.Entity;
import dev.apauley.general.Handler;

/*
 * Projectiles fired by player
 */

public class Bullet extends Creature{

	public Image bullet;
	
	public Bullet(Handler handler, float x, float y, float xMove, float yMove, Image bullet, String name, String group, int originID) {
		super(handler, x, y, handler.getGVar().get_DEFAULT_CREATURE_WIDTH() / 4, handler.getGVar().get_DEFAULT_CREATURE_HEIGHT() / 4, xMove, yMove, "BULLET", "ENEMY");
		this.bullet = bullet;
		//Boundary box for player
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = handler.getGVar().get_DEFAULT_CREATURE_WIDTH() / 4 - 2;
		bounds.height = handler.getGVar().get_DEFAULT_CREATURE_HEIGHT() / 4 - 2;
		
		this.name = name;
		this.group = group;
		this.speed = speed * 2;
		
		//Rename bullet with number appended based on bulletCount
		if(group.equals("PLAYER")) {
			id = handler.getWorld().getEntityManager().getBulletPlayerCount();
			
			//Increment player bullet count
			handler.getWorld().getEntityManager().setBulletPlayerCount(handler.getWorld().getEntityManager().getBulletPlayerCount() + 1);
			handler.getGame().getStatTracker().setBulletsFiredPlayer(handler.getGame().getStatTracker().getBulletsFiredPlayer() + 1);
		}
		if(group.equals("ENEMY")) {
			id = handler.getWorld().getEntityManager().getBulletEnemyCount();

			//Increment enemy bullet count
			handler.getWorld().getEntityManager().setBulletEnemyCount(handler.getWorld().getEntityManager().getBulletEnemyCount() + 1);
			handler.getGame().getStatTracker().setBulletsFiredEnemies(handler.getGame().getStatTracker().getBulletsFiredEnemies() + 1);
			
		}
		
		//Rename enemy with number appended based on enemyCount
		fullName = group + originID + name + id;
	
	}
	
	@Override
	public void tick() {
		
		if(!active)
			return;
		move();
		
		//If bullet collides with anything, remove it
		if(collisionWithTileTop || collisionWithTileBottom || collisionWithTileLeft || collisionWithTileRight || checkEntityCollisions(xMove, 0f) || checkEntityCollisions(0f, yMove))
			die();
	}
	
	@Override
	public void move() {
		
		//Set speed
		if(xMove > 0) xMove = speed;
		if(xMove < 0) xMove = -speed;
		if(xMove == 0) xMove = 0;
		if(yMove > 0) yMove = speed;
		if(yMove < 0) yMove = -speed;
		if(yMove == 0) yMove = 0;
		
		super.move();
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(bullet, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);	
	}

	@Override
	public void die() {
		setActive(false);
	}
}