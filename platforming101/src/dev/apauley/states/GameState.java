package dev.apauley.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

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

	//Game Constructor
	public GameState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);
		
		//Creates world (generic start)
		world = new World(handler);
		handler.setWorld(world);
		handler.getPhaseManager().setCurrentPhase(16);		
		
		handler.getWorld().getEntityManager().addEntity(new Bullet(handler,  5 + fontHeader.getSize() + 10 * 0 + 100, 5, 0, 0, Assets.yellow, "BULLET", "SYSTEM",0));
		handler.getWorld().getEntityManager().addEntity(new Bullet(handler,  5 + fontHeader.getSize() + 10 * 1 + 200, 5, 0, 0, Assets.yellow, "BULLET", "SYSTEM",0));
		handler.getWorld().getEntityManager().addEntity(new Bullet(handler,  5 + fontHeader.getSize() + 10 * 2 + 300, 5, 0, 0, Assets.yellow, "BULLET", "SYSTEM",0));
		handler.getWorld().getEntityManager().addEntity(new Bullet(handler,  5 + fontHeader.getSize() + 10 * 3 + 400, 5, 0, 0, Assets.yellow, "BULLET", "SYSTEM",0));
		handler.getWorld().getEntityManager().addEntity(new Bullet(handler,  5 + fontHeader.getSize() + 10 * 4 + 500, 5, 0, 0, Assets.yellow, "BULLET", "SYSTEM",0));
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
	}

	//Draws Level and player to screen
	@Override
	public void render(Graphics g) {		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight() / 10);
		//Shows BG Level
		//Note: Must render level before player do to proper layer positioning
		world.render(g);
		
		//Block to hide header if not above x phase
		if(handler.getPhaseManager().getCurrentPhase() > 15) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight() / 10);

			Text.drawStringShadow(g, "Bullets:", 5, 5 + fontHeader.getSize(), false, Color.WHITE, fontHeader);
			
			//g.drawImage(Assets.yellow,  90, 5, 50, 50, null);
			if(handler.getWorld().getEntityManager().getBulletPlayerCount() < 5) g.drawImage(Assets.yellow, 110, 5, 30, 40, null);
			if(handler.getWorld().getEntityManager().getBulletPlayerCount() < 4) g.drawImage(Assets.yellow, 145, 5, 30, 40, null);
			if(handler.getWorld().getEntityManager().getBulletPlayerCount() < 3) g.drawImage(Assets.yellow, 180, 5, 30, 40, null);
			if(handler.getWorld().getEntityManager().getBulletPlayerCount() < 2) g.drawImage(Assets.yellow, 215, 5, 30, 40, null);
			if(handler.getWorld().getEntityManager().getBulletPlayerCount() < 1) g.drawImage(Assets.yellow, 250, 5, 30, 40, null);
			
		}

		//Debug Text
		handler.getWorld().getDebugManager().render(g);

	}

}
