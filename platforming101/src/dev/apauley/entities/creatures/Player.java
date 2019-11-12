package dev.apauley.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Animation;
import dev.apauley.gfx.Assets;

/*
 * The player that our users will control.
 */

public class Player extends Creature{

	//Animations
	private Animation animDown, animUp, animRight, animLeft;
	private int animSpeed = 180;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

		//Boundary box for player
		bounds.x = Creature.DEFAULT_CREATURE_WIDTH / 4 + (int) (Creature.DEFAULT_CREATURE_WIDTH / 4 /2.5);
		bounds.y = Creature.DEFAULT_CREATURE_WIDTH / 2;
		bounds.width = Creature.DEFAULT_CREATURE_WIDTH / 4 + (int) (Creature.DEFAULT_CREATURE_WIDTH / 4 /4.5);
		bounds.height = Creature.DEFAULT_CREATURE_WIDTH / 2;	
		
		//Animations
		animDown 	= new Animation(500, Assets.player_down);
		animUp 		= new Animation(500, Assets.player_up);
		animRight 	= new Animation(500, Assets.player_right);
		animLeft 	= new Animation(500, Assets.player_left);
	}

	@Override
	public void tick() {
		
		//Animations
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();

		//Gets movement using speed
		getInput();		

		//Sets position using movement
		move();
		
		//Centers camera on player
		handler.getGameCamera().centerOnEntity(this);
	}
	
	//Takes user input and performs various actions
	private void getInput() {

		//Very important that every time we call this method we set xMove and yMove to 0
		xMove = 0;
		yMove = 0;
		
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
		
	}

	@Override
	public void render(Graphics g) {

		//Draw Player to screen
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int)  (y - handler.getGameCamera().getyOffset()), width, height, null);

		//Debug Bounding Box:
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset())
//				 , (int) (y + bounds.y - handler.getGameCamera().getyOffset())
//				 , bounds.width, bounds.height);
	}

	/*************** GETTERS and SETTERS ***************/

	//Gets current animation frame depending on movement/other
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			return animLeft.getCurrentFrame();
		} else  if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else  if (yMove < 0) {
			return animUp.getCurrentFrame();
		} else 
			return animDown.getCurrentFrame();
	}
	
}
