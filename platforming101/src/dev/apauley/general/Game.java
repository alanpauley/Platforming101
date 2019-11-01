package dev.apauley.general;

import dev.apauley.display.Display;

/*
 * Main class for game - holds all base code: 
 *     1.) Starts Game
 *     2.) Runs Game
 *     3.) Closes Game
 */

//Runnable allows Game to run on a thread (a mini program within the bigger program)
//Note: Requires a public void run(){} method otherwise will get error
public class Game {

	//Tracks General variables
	private Display display;
	
	public int width, height;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		
		display = new Display(title, width, height);
	}
	
}
