package dev.apauley.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.apauley.entities.Entity;
import dev.apauley.entities.creatures.Bullet;
import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.gfx.Text;
import dev.apauley.worlds.World;

/*
 * Where actual game play is at
 */

public class GameState extends State {

	//Holds current world
	private World world;
	
	//fonts
	private Font fontHeader = Assets.fontRobotoRegular30;
	
	//Tracks whether game is paused or stats are currently displaying or not
	private boolean paused, statDisplay;

	//Game Constructor
	public GameState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);
		
		//Creates world (generic start)
		world = new World(handler);
		handler.setWorld(world);
		handler.getPhaseManager().setCurrentPhase(21);
	}
	
	//Updates World
	@Override
	public void tick() {
		world.tick();
		
		//Cycles phases forward
		if(handler.getKeyManager().phasePrev)
			handler.getPhaseManager().setCurrentPhase(handler.getPhaseManager().getCurrentPhase() - 1);

		//Cycles phases back
		if(handler.getKeyManager().phaseNext)
			handler.getPhaseManager().setCurrentPhase(handler.getPhaseManager().getCurrentPhase() + 1);

		//Toggles pausing game
		if(handler.getKeyManager().pauseToggle && handler.getKeyManager().keyJustPressed(KeyEvent.VK_P) && handler.getPhaseManager().getCurrentPhase() > 19)
			togglePaused();
		//Toggles displaying stats
		if(handler.getKeyManager().statsToggle && handler.getKeyManager().keyJustPressed(KeyEvent.VK_O) && handler.getPhaseManager().getCurrentPhase() > 20)
			toggleStatDisplay();
		
	}

	//Draws Level and player to screen
	@Override
	public void render(Graphics g) {		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight() / 10);
		//Shows BG Level
		//Note: Must render level before player do to proper layer positioning
		world.render(g);
		
		//Block to hide header and bullet tracking if not above x phase
		if(handler.getPhaseManager().getCurrentPhase() > 15) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight() / 10);

			int x = 5;
			int x2 = x + 110;
			int y = 5;
			int w = 30; //Width
			int h = 40; //Height
			
			//Track Bullets
			Text.drawStringShadow(g, "Bullets:", x, y + fontHeader.getSize(), false, Color.WHITE, fontHeader);
			
			//Grey Base
			for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getBULLET_MAX(); i++)
				g.drawImage(Assets.greyDark, x2 + (w + 5) * i, y, w, h, null);

			//Yellow Bullets

			//Old Ammo system
			if(handler.getPhaseManager().getCurrentPhase() < 19) {
				for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getBULLET_MAX(); i++)
					if(handler.getWorld().getEntityManager().getBulletPlayerCount() < handler.getWorld().getEntityManager().getPlayer().getBULLET_MAX() - i) g.drawImage(Assets.yellow, x2 + (w + 5) * i, y, w, h, null);
			}
			
			//New Ammo system
			if(handler.getPhaseManager().getCurrentPhase() >= 19 && handler.getWorld().getEntityManager().getPlayer().getAmmo() >= 0) {
				for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getAmmo(); i++)
					g.drawImage(Assets.yellow, x2 + (w + 5) * i, y, w, h, null);
			}
			
		}			

		//Block to hide player health tracking if not above x phase
		if(handler.getPhaseManager().getCurrentPhase() > 16) {
			int x = 400;
			int x2 = x + 60;
			int y = 5;
			int w = 30;
			int h = 40;

			//Track Health
			Text.drawStringShadow(g, "HP:", 400, 5 + fontHeader.getSize(), false, Color.WHITE, fontHeader);

			//Grey Base
			for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH / 2; i++)
				g.drawImage(Assets.greyDark, x2 + (w + 5) * i, y, w, h, null);

			//Double layered:
				//Purple
				for(int i = 0; i < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH / 2; i++)
					if(handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH / 2 - handler.getWorld().getEntityManager().getPlayer().getHealth() < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH / 2 - i) g.drawImage(Assets.red, x2 + (w + 5) * i, y, w, h, null);
				//Red
				for(int i = 10; i < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH; i++)
					if(handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH - handler.getWorld().getEntityManager().getPlayer().getHealth() < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH - i) g.drawImage(Assets.purple, x2 + (w + 5) * (i - 10), y, w, h, null);
		}

		//Debug Text
		handler.getWorld().getDebugManager().render(g);

		//Display gun needs to be reloaded if on correct phase and timer > 0
		if(handler.getPhaseManager().getCurrentPhase() > 18 && handler.getWorld().getEntityManager().getPlayer().getEmptyGunTimer() > 0) {

			int x = 130;
			int y = 45;
			
			//Indicate to player that they need to reload
			Text.drawStringShadow(g, "*RELOAD*", x, y + fontHeader.getSize(), false, Color.WHITE, fontHeader);

		}
		
		//Displays paused message if game is paused (and stats not displayed
		if(paused && handler.getPhaseManager().getCurrentPhase() > 19 && !statDisplay)
			Text.drawStringShadow(g, "- PAUSED -", handler.getGame().getWidth() / 2, handler.getGame().getHeight() / 2 - fontHeader.getSize() / 2, true, Color.WHITE, fontHeader);

		//Displays stats if toggled on
		if(statDisplay && handler.getPhaseManager().getCurrentPhase() > 20)
			handler.getGame().getStatTracker().render(g);
	
	}
	
	/*************** GETTERS and SETTERS ***************/

	public boolean isPaused() {
		return paused;
	}

	//Pauses and unpauses game
	public void setPaused(boolean tf) {
			if(tf) {
				paused = true;
				
				//Pauses the game more or less
				handler.getWorld().getEntityManager().speedStop();
			} else {
				paused = false;
				statDisplay = false; //Hide this if displayed (don't need to check)

				//Resumes the game
				handler.getWorld().getEntityManager().speedResume();
			}		
	}

	public void togglePaused() {
		if(paused)
			setPaused(false);
		else
			setPaused(true);
	}

	public boolean isStatDisplay() {
		return statDisplay;
	}

	public void setStatDisplay(boolean tf) {
		if(tf) {
			
			//If game is NOT paused, pause it
			//if you just pause it again, will cause issues copying incorrect speeds
			if(!paused)
				setPaused(true);
			statDisplay = true;
		} else {
			statDisplay = false;
		}		
	}

	public void toggleStatDisplay() {
		if(statDisplay)
			setStatDisplay(false);
		else
			setStatDisplay(true);
	}
	
}
