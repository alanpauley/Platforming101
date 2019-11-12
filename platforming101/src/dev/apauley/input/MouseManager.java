package dev.apauley.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.apauley.ui.UIManager;

/*
 * Handles Mouse input by the user
 */

public class MouseManager implements MouseListener, MouseMotionListener {

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
		if(mouseX < 0 ) //Left Frame Wall
			mouseX = 0;
		if(mouseX > width) //Right Frame Wall
			mouseX = width;
		if(mouseY < 0) //Top Frame Wall
			mouseY = 0;
		if(mouseY > height) //Bottom Frame Wall
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
			leftPressed = true;
		if(e.getButton() == MouseEvent.BUTTON3) //Right Mouse Button
			rightPressed = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) //Left Mouse Button
			leftPressed = false;
		//Note: CodenMore did else if here, I did not, as I don't see reason/benefit
		if(e.getButton() == MouseEvent.BUTTON3) //Right Mouse Button
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
