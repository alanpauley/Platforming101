package dev.apauley.general;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.apauley.display.Display;
import dev.apauley.gfx.Assets;
import dev.apauley.states.GameState;
import dev.apauley.states.MenuState;
import dev.apauley.states.State;

/*
 * Main class for game - holds all base code: 
 *     1.) Starts Game
 *     2.) Runs Game
 *     3.) Closes Game
 */

//Runnable allows Game to run on a thread (a mini program within the bigger program)
//Note: Requires a public void run(){} method otherwise will get error
public class Game implements Runnable {

	//Tracks General variables
	private Display display;	
	public String title;
	public int width, height;
	
	//While Running = true, game will loop
	private boolean running = false;

	//Thread via Runnable
	private Thread thread;
	
	/*
	 * A way for computer to draw things to screen, using buffers
	 *    - Buffer is like a hidden computer screen, drawing behind the scenes
	 *    - Draw to first buffer, then push this to 2nd buffer, THEN push to actual screen
	 *    - Prevents flickering in game, of drawing to screen in real time
	 */
	private BufferStrategy bs;
	private Graphics g;
	
	//State objects
	private State gameState, menuState;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;		
	}
	
	//Called only once to initialize all of the graphics and get everything ready for game
	private void init() {
		
		//Sets display for Game instance
		display = new Display(title, width, height);
		
		//Loads all SpriteSheets to objects
		Assets.init();
		
		gameState = new GameState();
		menuState = new MenuState();
		State.setState(gameState);
	}
	
	//Update everything for game
	private void tick(){

		//Only tick if state is populated 
		if(State.getState() != null)
			State.getState().tick();
		
	}

	//Render everything for game
	private void render() {
		
		/*************** INITIAL SETUP (e.g. clear screen) ***************/
		
		//Get current buffer strategy of display
		bs = display.getCanvas().getBufferStrategy();
		
		//If bs doesn't exist, create one!
		//3 should be max number of buffers we ever need (may not work with >3)
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		//Graphics object is like our (magical) paint brush, way of drawing
		g = bs.getDrawGraphics();
		
		//Clear screen
		g.clearRect(0, 0, width, height);

		/*************** END INITIAL SETUP ***************/

		/*************** DRAW HERE ***************/

		//Only render if state is populated 
		if(State.getState() != null)
			State.getState().render(g);

		/*************** END DRAWING ***************/
		
		//Work buffer magic (presumably to transfer between buffers, ending at screen
		bs.show();
		
		//Discard graphics object properly
		g.dispose();
		
	}
	
	//Majority of our game code will be in here
	// - Required to implement Runnable. 
	// - Is called by start() method.
	public void run() {
	
		//initialize all of the graphics and get everything ready for game
		init();
		
		int fps = 60;
		
		//1 billion nanoseconds within a second
		long nanoSeconds = 1000000000;
		
		//Below translates to 1 per second, but nanoseconds is more exact so allows for more flexibility.
		double timePerTick = nanoSeconds/fps;

		double delta = 0;
		long now;
		
		//Returns current time of computer in nanoseconds.
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		//Game Loop:
		// 1.) Update all variables, positions of objects, etc.
		// 2.) Render (draw) everything to the screen
		// 3.) Repeat
		while(running){
			//Below will do how much time we have before we can call tick() and render() again.
			/*
			 * Delta is difference of now/last in nano seconds, divided by time per tick
			 * This is essentially the percentage of time per tick that has passed
			 * So delta reaching 1 or more is 100% of time per tick, thus time to tick
			 * 
			 * Not sure why doing it this way instead of just doing (now - last),
			 * and then checking if delta is >= timePerTick?
			 */
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			//If enough time has elapsed, can run tick() and render().
			if(delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= nanoSeconds) {
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		//Just an extra check in case the above doesn't stop it from running.
		stop();
		
	}
	
	//"Synchronized" is keyword for whenever we're working with threads directly (starting/stopping).
	// - Prevents things from getting "messed up"
	public synchronized void start() {
		
		//Check if already running game loop
		if(running) {
			return;
		} else {
			
			//Set running = true. Used in game loop
			running = true;
			
			//Initialize thread and pass in Game class
			thread = new Thread(this);
			
			//start() will actually call the run() method.
			thread.start();		
		}
	}
	
	//Stops thread safely
	// - Requires Try/Catch otherwise will get error
	public synchronized void stop() {

		//Check if already running game loop
		if(!running) {
			return;
		} else {

			//Set running = false. Used in game loop
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
