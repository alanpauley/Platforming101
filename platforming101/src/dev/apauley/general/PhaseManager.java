package dev.apauley.general;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * Handles changing game Phase
 */

public class PhaseManager {

	private Handler handler;
	
	//handle phases of game construction (/res/references/First Platformer Checklist.xlsx)
	private int currentPhase;
	
	//tracks how long currentPhaseName is so we can show debugger "Prettier" <3
	private int currentPhaseNameLength;
	
	private ArrayList<String> phases;
	
	//Phase Timer
	private long lastPhaseTimer, phaseCooldown = 600, phaseTimer = phaseCooldown;

	public PhaseManager(Handler handler) {

		this.handler = handler;

		//Phase descriptions
		phases = new ArrayList<String>();
		//1 - Initialization
			phases.add("Player spawns on screen");
			phases.add("Background visible on screen");
			phases.add("Floor/Walls visible on screen");
			phases.add("Implement gravity");
			phases.add("Player collides with environment correctly");
		//2 - Movement
			phases.add("Player can move");
			phases.add("Player can jump");
			phases.add("Player can shoot");
			phases.add("Player can run");
			phases.add("Camera will follow player");
		//3 - Environment
			phases.add("Enemies spawn");
			phases.add("Enemies collide with environment");
			phases.add("Enemies move");
			phases.add("Enemies collide with player");
			phases.add("Enemies shoot");
			phases.add("Shots collide with player/enemies");
		//4 - UI
			phases.add("Bullets tracked");
			phases.add("Reload implemented");
			phases.add("Score tracked (kills, time elpased, etc.)");
			phases.add("Pause button (stop time)");
			phases.add("Menu screen (utilizes pause button)");
			phases.add("Slow Time (death scene)");
			phases.add("Death Screen");
			phases.add("Zoom in");
			phases.add("Zoom out");
		//5 - Advanced
			phases.add("New level after x enemies killed");
			phases.add("Spawn harder enemies");
			phases.add("Random drops (health, items, weapons)");
			phases.add("Special abilities");
			phases.add("High score chart");
			phases.add("Save game");
			phases.add("Continue game");
			phases.add("Replay (Track player movement)");
				
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
		if(currentPhase > 32)
			currentPhase = 32;
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
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			loadLevel = 3;
			break;
		default:
			loadLevel = 4;
			break;
		}

		//Sets currentPhaseNameLength
		switch(currentPhase) {
		case 0: currentPhaseNameLength = 340; break;
		case 1: currentPhaseNameLength = 380; break;
		case 2: currentPhaseNameLength = 375; break;
		case 3: currentPhaseNameLength = 280; break;
		case 4: currentPhaseNameLength = 500; break;
		case 5: currentPhaseNameLength = 265; break;
		case 6: currentPhaseNameLength = 255; break;
		case 7: currentPhaseNameLength = 265; break;
		case 8: currentPhaseNameLength = 245; break;
		case 9: currentPhaseNameLength = 345; break;
		case 10: currentPhaseNameLength = 265; break;
		case 11: currentPhaseNameLength = 430; break;
		case 12: currentPhaseNameLength = 255; break;
		case 13: currentPhaseNameLength = 375; break;
		case 14: currentPhaseNameLength = 260; break;
		case 15: currentPhaseNameLength = 428; break;
		case 16: currentPhaseNameLength = 260; break;
		case 17: currentPhaseNameLength = 310; break;
		case 18: currentPhaseNameLength = 480; break;
		case 19: currentPhaseNameLength = 345; break;
		case 20: currentPhaseNameLength = 450; break;
		case 21: currentPhaseNameLength = 350; break;
		case 22: currentPhaseNameLength = 248; break;
		case 23: currentPhaseNameLength = 195; break;
		case 24: currentPhaseNameLength = 207; break;
		case 25: currentPhaseNameLength = 418; break;
		case 26: currentPhaseNameLength = 333; break;
		case 27: currentPhaseNameLength = 478; break;
		case 28: currentPhaseNameLength = 269; break;
		case 29: currentPhaseNameLength = 272; break;
		case 30: currentPhaseNameLength = 222; break;
		case 31: currentPhaseNameLength = 255; break;
		case 32: currentPhaseNameLength = 420; break;
		default: currentPhaseNameLength = 0; break;
		}
		
		//Remove Entities
		
		//Load appropriate world
		handler.getWorld().loadWorld(handler.getWorld().getWorldPaths(loadLevel));
		
		//Reset player
		handler.getWorld().getEntityManager().getPlayer().resetPlayer();

		//Reset GameCamera
		handler.getGameCamera().resetGameCamera();
		
		//Reset phaseTimer
		phaseTimer = 0;		
	}

	public String getCurrentPhaseName() {
		return phases.get(currentPhase);
	}

	//Gets how long currentPhaseName is so we can show debugger "Prettier" <3
	public int getCurrentPhaseNameLength() {
		return currentPhaseNameLength;
	}

	//Sets how long currentPhaseName is so we can show debugger "Prettier" <3
	public void setCurrentPhaseNameLength(int currentPhaseNameLength) {
		this.currentPhaseNameLength = currentPhaseNameLength;
	}

}
