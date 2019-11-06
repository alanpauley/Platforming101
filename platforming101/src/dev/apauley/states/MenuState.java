package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Game;
import dev.apauley.gfx.Assets;

/*
 * Where menu is at
 */

public class MenuState extends State {

	//Game Constructor
	public MenuState(Game game) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(game);

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
