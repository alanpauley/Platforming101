package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.gfx.Assets;

/*
 * Where menu is at
 */

public class MenuState extends State {

	//Game Constructor
	public MenuState() {
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {		
		g.drawImage(Assets.grass,5,0,null);
	}

}
