package dev.apauley.entities.creatures;

import java.awt.Graphics;

import dev.apauley.general.Game;
import dev.apauley.gfx.Assets;

/*
 * The player that our users will control.
 */

public class Player extends Creature{

	private Game game;
	
	public Player(Game game, float x, float y) {
		super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.game = game;
	}

	@Override
	public void tick() {
		
		//Gets movement using speed
		getInput();		

		//Sets position using movement
		move();
	}
	
	//Takes user input and performs various actions
	private void getInput() {

		//Very important that every time we call this method we set xMove and yMove to 0
		xMove = 0;
		yMove = 0;
		
		//Setting x/y move to a certain speed, THEN moving player that much
		
		//Handles player Movement
		if(game.getKeyManager().up)
			yMove = -speed;
		if(game.getKeyManager().down)
			yMove = speed;
		if(game.getKeyManager().right)
			xMove = speed;
		if(game.getKeyManager().left)
			xMove = -speed;
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player, (int) x, (int) y, width, height, null);
	}

}
