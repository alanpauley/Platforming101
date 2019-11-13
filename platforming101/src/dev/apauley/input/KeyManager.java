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
	
	//Specific keys we're using
	public boolean /*DIRECTIONS*/ 
						up, down, left, right,
				   /*ATTACKS WITH ARROW KEYS*/
						aUp, aDown, aLeft, aRight;

	//Constructor that creates base key array
	//And other arrays of same length, for controlling key press timing
	public KeyManager() {
		keys = new boolean[256];
	}

	//Updates and gets key presses
	public void tick() {

		//*DIRECTION*/
		//move player
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		//*ATTACKS*/
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

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
