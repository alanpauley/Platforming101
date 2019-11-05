package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.gfx.Assets;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Game Constructor
	public GameState() {
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {		
		g.drawImage(Assets.dirt,0,0,null);
	}

}
