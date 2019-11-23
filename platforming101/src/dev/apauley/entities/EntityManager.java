package dev.apauley.entities;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.apauley.entities.creatures.Bullet;
import dev.apauley.entities.creatures.Player;
import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;

/*
 * Handles Entities
 */

public class EntityManager {

	private Handler handler;
	private Player player;
	
	//Counts Enemies
	private int playerCount = 1, enemyCount, bulletPlayerCount, bulletEnemyCount;
	
	//An ArrayList of Entities that holds all entities. 
	//- like Entity[] but has no size (can add/subtract at will)
	private ArrayList<Entity> entities;

	//Temporary holding place for entities that are added (possibly removed) when done outside of this class
	private ArrayList<Entity> entitiesLimbo;

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
		entitiesLimbo = new ArrayList<Entity>();
		
		//Puts player into the array list of entities
		addEntity(player);
	}
	
	public void tick() {
		
		//Loop through entities to tick them all using an iterator
		Iterator<Entity> it = entities.iterator();
		
		//we didn't use the list for loop like in render() below, because it will cause issues when you deal with collisions.
		//As long as the iterator object has items, it will continue, even if we remove some entities mid-way
		//This is important so that we don't just exit the loop and ignore any objects getting their updated tick()
		while(it.hasNext()) {
			Entity e = it.next(); //The same as saying entities[i] or entities.get(i), but for an iterator
			e.tick();			
			e.flash(); //Decrements flash
			//If e is no longer active, remove
			if(!e.isActive())
				//it.remove() will safely and properly remove entity from the list, opposed to e.remove() which would cause issues just yanking it out midway, skipping over entities. 
				it.remove();
		}
		
		//Resort entities based on renderSorter
		entities.sort(renderSorter);

		//Add entities from entitiesLimbo to entities then purge entitiesLimbo list
		entitiesLimboPurge();
		
	}
	
	public void render(Graphics g) {
		
		//Loop through entities to draw them all to the screen
		//Like a standard for loop, but loops through all entities in the entities list for us.
		//The reason we didn't do this above in tick() is because it would cause issues when you deal with collisions.
		for(Entity e : entities) { 
			e.render(g);
		}
		
		//Render everything that should show up on top, AFTER everything else
		player.postRender(g);
	}
	
	//Lower each entity's speed
	public void speedDown() {

		//Loop through entities
		for(Entity e : entities) { 
			e.setSpeed(e.getSpeed() - 1f);

			//Don't let speed go below MIN_SPEED
			if(e.getSpeed() <= e.MIN_SPEED)
				e.setSpeed(e.MIN_SPEED);
		}
		
	}

	//Increase each entity's speed
	public void speedUp() {

		//Loop through entities
		for(Entity e : entities) { 
			e.setSpeed(e.getSpeed() + 1f);

			//Don't let speed go above MAX_SPEED
			if(e.getSpeed() >= e.MAX_SPEED)
				e.setSpeed(e.MAX_SPEED);
		}
		
	}

	//Remove all entities from the Entity Array List
	public void removeAllEntities(ArrayList<Entity> entitiesList) {
		//Loop through entities to tick them all using an iterator
		Iterator<Entity> it = entitiesList.iterator();

		while(it.hasNext()) {
			Entity e = it.next(); //The same as saying entities[i] or entities.get(i), but for an iterator
			
			//Don't remove player
			//if(e.name != "PLAYER")
				it.remove();
		}
	}
	
	//We don't actually use this, I can probably delete it...
	//Take an entity and remove it from the Entity Array List
//	public void removeEntity(Entity e) {
//		entities.remove(e);
//	}
	
	//Take an entity and add to the Entity Array List (so it can be ticked and rendered)
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	//Removes all entities from entitiesLimbo >> entities
	public void entitiesLimboPurge() {
		
		//If entitiesLimbo is empty, exit
		if(entitiesLimbo.size() == 0)
			return;
		
		//otherwise, add them to entities list
		for(Entity e : entitiesLimbo) {
			addEntity(e);
		}
		
		//safely remove all entities from entitiesLimbo
		removeAllEntities(entitiesLimbo);
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
		
	public ArrayList<Entity> getEntitiesLimbo() {
		return entitiesLimbo;
	}

	public void setEntitiesLimbo(ArrayList<Entity> entitiesLimbo) {
		this.entitiesLimbo = entitiesLimbo;
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getBulletPlayerCount() {
		return bulletPlayerCount;
	}

	public void setBulletPlayerCount(int bulletPlayerCount) {
		this.bulletPlayerCount = bulletPlayerCount;
	}

	public int getBulletEnemyCount() {
		return bulletEnemyCount;
	}

	public void setBulletEnemyCount(int bulletEnemyCount) {
		this.bulletEnemyCount = bulletEnemyCount;
	}

	
}
