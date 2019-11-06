package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Game;

/*
 * Base class to handle all game states
 * For example game (main game play), menu, etc.
 */

public abstract class State {
	
	private static State currentState = null;
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
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
