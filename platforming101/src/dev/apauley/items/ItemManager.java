package dev.apauley.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.apauley.general.Handler;

/*
 * Handles Items (only those in the game, lying on the ground)
 */

public class ItemManager {
	
	private Handler handler;
	private ArrayList<Item> items;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	
	//See EntityManager.class for explanation of Iterator below
	public void tick() {
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item i = it.next();
			i.tick();
			
			//If count of item equals -1, item is picked up and we should remove it from the game world and add to player's inventory
			if(i.isPickedUp())
				it.remove();
		}
	}
	
	public void render(Graphics g) {
		for(Item i : items){
			i.render(g);
		}
	}

	public void addItem(Item i) {
		
		//Items have their handler = null when initialized, so we want to update the handler when adding items
		i.setHandler(handler);
		items.add(i);
	}

	/*************** GETTERS and SETTERS ***************/
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
