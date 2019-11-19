package dev.apauley.general;

import java.awt.event.KeyEvent;

/*
 * Handles changing game Phase
 */

public class PhaseManager {

	private Handler handler;
	
	//handle phases of game construction (/res/references/First Platformer Checklist.xlsx)
	private int currentPhase;
	
	//Phase Timer
	private long lastPhaseTimer, phaseCooldown = 600, phaseTimer = phaseCooldown;

	public PhaseManager(Handler handler) {

		this.handler = handler;
		
		//start game phasing at beginning
		currentPhase = 0;

	}

	/*************** GETTERS and SETTERS ***************/

	//gets Current Phase
	public int getCurrentPhase() {
		return currentPhase;
	}

	//sets Current Phase
	public void setCurrentPhase(int currentPhase) {

		int loadLevel;
		
		//Update phaseTimer
		phaseTimer += System.currentTimeMillis() - lastPhaseTimer;
		lastPhaseTimer = System.currentTimeMillis();
		
		//if user is decrementing|incrementing phase and respective key was just pressed, reset phase timer so they can click through it faster
		if((this.currentPhase > currentPhase && handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA))
			|| (this.currentPhase < currentPhase && handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)))
			phaseTimer = phaseCooldown;
		
		//check if can switch phase yet
		if(phaseTimer < phaseCooldown)
			return;

		//Prevent going to an undefined phase (need to be between 1 and ???)
		if(currentPhase < 0)
			currentPhase = 0;
		if(currentPhase > 20)
			currentPhase = 20;
		this.currentPhase = currentPhase;
		//System.out.println("current Phase: " + currentPhase);

		switch(currentPhase) {
		case 0:
			loadLevel = currentPhase;
			break;
		case 1:
			loadLevel = currentPhase;
			break;
		case 2:
			loadLevel = currentPhase;
			break;
		default:
			loadLevel = 3;
			break;
		}
		
		//Remove Entities
		
		//Load appropriate world
		handler.getWorld().loadWorld(handler.getWorld().getWorldPaths(loadLevel));
		
		//Reset player
		handler.getWorld().getEntityManager().getPlayer().resetPlayer();

		//Reset phaseTimer
		phaseTimer = 0;		
	}
}
