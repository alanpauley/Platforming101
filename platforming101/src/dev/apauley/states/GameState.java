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
	
	//Tracks whether stats are currently displaying or not
	private boolean statDisplay;

	//Game Constructor
	public GameState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);
		
		//Creates world (generic start)
		world = new World(handler);
		handler.setWorld(world);
		handler.getPhaseManager().setCurrentPhase(20);
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

		//Toggles displaying stats
		if(handler.getKeyManager().statsToggle && handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
			if(statDisplay) {
				statDisplay = false;

				//Resumes the game more or less
				handler.getWorld().getEntityManager().speedResume();
				
			}
			else {
				statDisplay = true;
				
				//Pauses the game more or less
				handler.getWorld().getEntityManager().speedStop();
				
			}
		}
		
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
		
		//Displays stats if toggled
//		if(statDisplay) {
//
//			int x = 5;
//			int y = 5;
//			
//			g.setColor(new Color(0,0,0,180));
//			g.fillRect(x, y, handler.getGame().getWidth() - x * 2, handler.getGame().getHeight() - y * 2);
//			
//			Text.drawStringShadow(g, "Enemies Fought: ", x, y + fontHeader.getSize(), false, Color.WHITE, fontHeader);
//			
//		}
		
	
	}

}
