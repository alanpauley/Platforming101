package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.entities.creatures.Player;
import dev.apauley.general.Game;
import dev.apauley.tiles.Tile;

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
		
		Tile.tiles[0].render(g, 64 * 0, 0);
		Tile.tiles[1].render(g, 64 * 1, 0);
		Tile.tiles[2].render(g, 64 * 2, 0);
		Tile.tiles[3].render(g, 64 * 3, 0);
	}

}
