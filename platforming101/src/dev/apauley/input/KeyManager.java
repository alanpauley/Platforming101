package dev.apauley.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Handles keyboard input
 * Keys pressed, released, etc.
 */
public class KeyManager implements KeyListener {

	//Array to determine true (pressed) or false (not pressed) for every key 
	//Location in array determined by key code of key (see getKeyCode method calls)
	private boolean[] keys;
	
	//Tracks whether key was JUST pressed
	private boolean[] justPressed;
	
	//Tracks whether user can press the key or not
	private boolean[] cantPress;
	
	//Specific keys we're using
	public boolean /*DIRECTIONS*/ 
						up, down, left, right
				   /*ACTIONS*/
					  , jump
				   /*ATTACKS WITH ARROW KEYS*/
					  , aUp, aDown, aLeft, aRight
				   /*SYSTEM KEYS*/
					  , phasePrev, phaseNext
					  , debugPlayer, debugSystem, debugAll;

	//Constructor that creates base key array
	//And other arrays of same length, for controlling key press timing
	public KeyManager() {
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}

	//Updates and gets key presses
	public void tick() {
		
		//Loop through keys
		for(int i = 0; i < keys.length; i++) {
			
			//If cannot press particular key and key is no longer being pressed, key has been released and user should be able to press again
			if(cantPress[i] && !keys[i]) {
				cantPress[i] = false;

			//Else if key was just pressed, set just pressed to be false and cantpress to be true
			} else if(justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			
			//If you CAN press the key and it's currently being pressed, set justpressed = true
			if(!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
		}
		
		//*DIRECTION*/
		//move player
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		/*ACTIONS*/
		jump = keys[KeyEvent.VK_SPACE];
		//*ATTACKS*/
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];
		//*SYSTEM*/
		phasePrev= keys[KeyEvent.VK_COMMA];
		phaseNext = keys[KeyEvent.VK_PERIOD];

		debugPlayer = keys[KeyEvent.VK_F1];
		debugSystem = keys[KeyEvent.VK_F2];
		debugAll = keys[KeyEvent.VK_F12];
		
	}
	
	//Checks whether a key was just pressed
	public boolean keyJustPressed(int keyCode){

		//This exits before starting code if we ever have a bad key entered by player
		if(keyCode < 0 || keyCode >= keys.length)
			return false;

		// Key that was just pressed
		return justPressed[keyCode];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		//This exits before starting code if we ever have a bad key entered by player
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		
		// Whenever key is pressed
		keys[e.getKeyCode()] = true;
		//System.out.println("Pressed: " + e.getKeyChar()); //Debug
	}

	@Override
	public void keyReleased(KeyEvent e) {

		// Whenever key is released
		keys[e.getKeyCode()] = false;		
		//System.out.println("Released: " + e.getKeyChar()); //Debug
	}

}
