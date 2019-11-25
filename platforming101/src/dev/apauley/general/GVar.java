package dev.apauley.general;

/*
 * Handles global variables
 */

public class GVar {

	public Handler handler;
	
	//Default Creature Values
	private final int DEFAULT_CREATURE_WIDTH = 64
			              , DEFAULT_CREATURE_HEIGHT = DEFAULT_CREATURE_WIDTH;
	
	//Gravity on creatures
	private final float DEFAULT_GRAVITY = 9.8f;
	
	//Refactored speed to account for different sizes
	private final float DEFAULT_SPEED = 3.0f + (DEFAULT_CREATURE_WIDTH / 64 * 1.25f) 
							, DEFAULT_RUN = DEFAULT_SPEED * 1.5f
							, MAX_SPEED = 20f
							, MIN_SPEED = 0; 

	//Tracks current Global Speed for ALL entities
	private float gSpeed;
	
	public GVar(Handler handler) {
		gSpeed = DEFAULT_SPEED;
	}

	/*************** GETTERS and SETTERS ***************/
		
	public float getGSpeed() {
		return gSpeed;
	}

	public void setGSpeed(float gSpeed) {
		this.gSpeed = gSpeed;
	}

	/*************** FINAL GETTERS and SETTERS ***************/
	
	public int get_DEFAULT_CREATURE_WIDTH() {
		return DEFAULT_CREATURE_WIDTH;
	}

	public int get_DEFAULT_CREATURE_HEIGHT() {
		return DEFAULT_CREATURE_HEIGHT;
	}

	public float get_DEFAULT_GRAVITY() {
		return DEFAULT_GRAVITY;
	}

	public float getDefaultSpeed() {
		return DEFAULT_SPEED;
	}

	public float getDefaultRun() {
		return DEFAULT_RUN;
	}

	public float getMaxSpeed() {
		return MAX_SPEED;
	}

	public float getMinSpeed() {
		return MIN_SPEED;
	}
	
}
