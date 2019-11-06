package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.entities.creatures.Player;
import dev.apauley.gfx.Assets;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current Player and Level
	private Player player;

	//Game Constructor
	public GameState() {
		
		//Creates new Player
		player = new Player(100,100);		
	}
	
	@Override
	public void tick() {
		player.tick();
		
	}

	@Override
	public void render(Graphics g) {		

		//Shows Player
		player.render(g);
	}

}
