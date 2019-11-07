package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.entities.creatures.Player;
import dev.apauley.general.Game;
import dev.apauley.tiles.Tile;
import dev.apauley.worlds.World;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current Player and Level
	private Player player;
	private World world;

	//Game Constructor
	public GameState(Game game) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(game);
		//Creates new Player
		player = new Player(game,100,100);	
		world = new World(game, "res/worlds/world1.txt");
	}
	
	//Updates Player and Level
	@Override
	public void tick() {
		world.tick();
		player.tick();
	}

	//Draws Level and player to screen
	@Override
	public void render(Graphics g) {		

		//Shows BG Level
		//Note: Must render level before player do to proper layer positioning
		world.render(g);

		//Shows Player
		player.render(g);
		
		Tile.tiles[0].render(g, 64 * 9, 64 * 4);
		Tile.tiles[1].render(g, 64 * 9, 64 * 5);
		Tile.tiles[2].render(g, 64 * 9, 64 * 6);
		Tile.tiles[3].render(g, 64 * 9, 64 * 7);
	}

}
