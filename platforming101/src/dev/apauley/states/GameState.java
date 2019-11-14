package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.worlds.World;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current world
	private World world;
	
	//Game Constructor
	public GameState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);
		
		//Creates world (generic start)
		world = new World(handler);
		handler.setWorld(world);
		
		
	}
	
	//Updates World
	@Override
	public void tick() {
		world.tick();
		
		//Cycles phases forward
		if(handler.getKeyManager().phasePrev)
			handler.getPhaseManager().setCurrentPhase(handler.getPhaseManager().getCurrentPhase() - 1);

		//Cycles phases back
		if(handler.getKeyManager().phaseNext)
			handler.getPhaseManager().setCurrentPhase(handler.getPhaseManager().getCurrentPhase() + 1);
	}

	//Draws Level and player to screen
	@Override
	public void render(Graphics g) {		

		switch(handler.getPhaseManager().getCurrentPhase()){
		case 0:
			handler.getWorld().getEntityManager().getPlayer().setActive(true);
		}
		
		//Shows BG Level
		//Note: Must render level before player do to proper layer positioning
		world.render(g);

	}

}
