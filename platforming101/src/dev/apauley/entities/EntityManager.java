package dev.apauley.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import dev.apauley.entities.creatures.Player;
import dev.apauley.general.Handler;

/*
 * Handles Entities
 */

public class EntityManager {

	private Handler handler;
	private Player player;
	
	//An ArrayList of Entities that holds all entities. 
	//- like Entity[] but has no size (can add/subtract at will)
	private ArrayList<Entity> entities;
	
	//Used to compare entities (namely the order which we render them)
	//Return -1 if A should be rendered BEFORE B
	//Return +1 if A should be rendered AFTER  B
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}	
		
	};
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		
		//Puts player into the array list of entities
		addEntity(player);
	}
	
	public void tick() {
		
		//Loop through entities to tick them all
		//we didn't use the list for loop like in render() below, because it will cause issues when you deal with collisions.
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i); //The same as saying entities[i] but for lists
			e.tick();
			
			//If e is no longer active, remove
			if(!e.isActive())
				entities.remove(e);
		}
		
		//Resort entities based on renderSorter
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g) {
		
		//Loop through entities to draw them all to the screen
		//Like a standard for loop, but loops through all entities in the entities list for us.
		//The reason we didn't do this above in tick() is because it would cause issues when you deal with collisions.
		for(Entity e : entities) { 
			e.render(g);
		}
	}

	//Take an entity and add to the Entity Array List (so it can be ticked and rendered)
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	/*************** GETTERS and SETTERS ***************/

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
		
}
