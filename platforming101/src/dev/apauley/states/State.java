package dev.apauley.states;

import java.awt.Graphics;

/*
 * Base class to handle all game states
 * For example game (main game play), menu, etc.
 */

public abstract class State {
	
	private static State currentState = null;
	
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
