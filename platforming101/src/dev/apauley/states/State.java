package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Handler;

/*
 * Base class to handle all game states
 * For example game (main game play), menu, etc.
 */

public abstract class State {
	
	private static State currentState = null;
	
	//We want an instance of our game class in all our state classes
	//But now going to reference game class via handler
	protected Handler handler;

	public State(Handler handler) {
		this.handler = handler;
	}
	
	//Unused here (abstract)
	public abstract void tick();
	public abstract void render(Graphics g);
	
	/***************** GETTERS AND SETTERS *****************/
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	

	
}
