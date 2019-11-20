package dev.apauley.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.apauley.ui.UIManager;

/*
 * Handles Mouse input by the user
 */

public class MouseManager implements MouseListener, MouseMotionListener {

	//Array to determine true (clicked) or false (not clicked) for every mouse click 
	//Location in array determined by mouse click key (see getClickCode method calls)
	private boolean[] clicks;
	
	//Tracks whether key was JUST pressed
	private boolean[] justPressed;
	
	//Tracks whether user can press the key or not
	private boolean[] cantPress;
	
	//Tracks whether mouse buttons are currently being pressed
	private boolean leftPressed, rightPressed;
	
	//Tracks the current position of the mouse
	private int mouseX, mouseY;
	
	//tracks the canvas size
	private int width, height;
	
	private UIManager uiManager;
	
	public MouseManager(int width, int height) {
		this.width = width;
		this.height = height;
		clicks = new boolean[256];
		justPressed = new boolean[clicks.length];
		cantPress = new boolean[clicks.length];
	}

	//Updates and gets mouse clicks
	public void tick() {
		
		//Loop through keys
		for(int i = 0; i < clicks.length; i++) {
			
			//If cannot press particular key and key is no longer being pressed, key has been released and user should be able to press again
			if(cantPress[i] && !clicks[i]) {
				cantPress[i] = false;

			//Else if key was just pressed, set just pressed to be false and cantpress to be true
			} else if(justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			
			//If you CAN press the key and it's currently being pressed, set justpressed = true
			if(!cantPress[i] && clicks[i]) {
				justPressed[i] = true;
			}
		}

		//*DIRECTION*/
		//move player
		leftPressed = clicks[MouseEvent.BUTTON1];
		rightPressed = clicks[MouseEvent.BUTTON3];
	}
	
	//Checks whether a key was just pressed
	public boolean keyJustPressed(int clickCode){

		//This exits before starting code if we ever have a bad key entered by player
		if(clickCode < 0 || clickCode >= clicks.length) {
			return false;
		}
		// Key that was just pressed
		return justPressed[clickCode];
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	/*************** GETTERS and SETTERS ***************/

	//Gets whether the left mouse button is pressed or not
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	//Gets whether the right mouse button is pressed or not
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	//Gets the mouse X Position
	public int getMouseX() {
		return mouseX;
	}

	//Gets the mouse Y Position
	public int getMouseY() {
		return mouseY;
	}
	/*************** IMPLEMENTED METHODS ***************/
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();	
		
		//Extra checks and fixes for going off screen
			
		//For whatever reason, moving left (slowly) still seems to break and not work
		//The mouse image will stop then JUMP back to the right a few pixels and settle down at about 3 pixels (and down for whatever reason)
		if(mouseX <= 0 ) //Left Frame Wall
			mouseX = 0;
		if(mouseX >= width) //Right Frame Wall
			mouseX = width;
		if(mouseY <= 0) //Top Frame Wall
			mouseY = 0;
		if(mouseY >= height) //Bottom Frame Wall
			mouseY = height;
		
		//if uiManager exists, pass it through the uiManager to run that specific code
		if(uiManager != null)
			uiManager.onMouseMove(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) //Left Mouse Button
			clicks[MouseEvent.BUTTON1] = true;
			leftPressed = true;
		if(e.getButton() == MouseEvent.BUTTON3) //Right Mouse Button
			clicks[MouseEvent.BUTTON3] = true;
			rightPressed = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) //Left Mouse Button
			clicks[MouseEvent.BUTTON1] = false;
			leftPressed = false;
		//Note: CodenMore did else if here, I did not, as I don't see reason/benefit
		if(e.getButton() == MouseEvent.BUTTON3) //Right Mouse Button
			clicks[MouseEvent.BUTTON3] = false;
			rightPressed = false;
		
		//if uiManager exists, pass it through the uiManager to run that specific code
		if(uiManager != null)
			uiManager.onMouseRelease(e);
			
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
