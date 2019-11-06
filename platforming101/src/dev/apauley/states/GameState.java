package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.entities.creatures.Player;
import dev.apauley.general.Game;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current Player and Level
	private Player player;

	//Game Constructor
	public GameState(Game game) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(game);
		//Creates new Player
		player = new Player(game,100,100);		
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
