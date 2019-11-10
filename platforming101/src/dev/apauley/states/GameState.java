package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.entities.creatures.Player;
import dev.apauley.entities.statics.Tree;
import dev.apauley.general.Handler;
import dev.apauley.worlds.World;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current Player and Level
	private Player player;
	private World world;

	//Game Constructor
	public GameState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);
		
		//Creates world
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
		//Creates new Player
	}
	
	//Updates Player and Level
	@Override
	public void tick() {
		world.tick();
	}

	//Draws Level and player to screen
	@Override
	public void render(Graphics g) {		

		//Shows BG Level
		//Note: Must render level before player do to proper layer positioning
		world.render(g);

	}

}
