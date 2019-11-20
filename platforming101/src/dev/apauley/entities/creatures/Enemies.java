package dev.apauley.entities.creatures;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;

/*
 * Projectiles fired by player
 */

public class Enemies extends Creature{

	public Enemies(Handler handler, float x, float y, float xMove, float yMove) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH / 4, Creature.DEFAULT_CREATURE_HEIGHT / 4, xMove, yMove, "BULLET");
		
		//Boundary box for player
		bounds.x = 1;
		bounds.y = 1;
		bounds.width = Creature.DEFAULT_CREATURE_WIDTH / 4 - 2;
		bounds.height = Creature.DEFAULT_CREATURE_HEIGHT / 4 - 2;
		
	}
	
	@Override
	public void tick() {
		move();
		
		//If bullet collides with anything, remove it
		if(collisionWithTileTop || collisionWithTileBottom || collisionWithTileLeft || collisionWithTileRight || checkEntityCollisions(xMove, 0f) || checkEntityCollisions(0f, yMove))
			die();
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);	
	}

	@Override
	public void die() {
		setActive(false);
	}
}