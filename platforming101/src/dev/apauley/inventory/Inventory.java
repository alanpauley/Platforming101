package dev.apauley.inventory;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.apauley.general.Handler;
import dev.apauley.items.Item;

/*
 * Handles inventory
 */

public class Inventory {

	private Handler handler;
	
	//Tracks whether inventory is currently active or not
	private boolean active = false;
	
	//Stores inventory items
	private ArrayList<Item> inventoryItems;
	
	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
	}
	
	public void tick() {
		
		//If Inventory key is pressed, toggle inventory on/off
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
			active = !active;
		
		//Do not tick unless inventory is active
		if(!active)
			return; 
		
		System.out.println("INVENTORY");
		for(Item i : inventoryItems) {
			System.out.println(i.getName() + ": " + i.getCount());
		}
		
	}
	
	public void render(Graphics g) {

		//Do not render unless inventory is active
		if(!active)
			return;
		
	}
	
	/*************** INVENTORY METHODS ***************/
	
	//Adds item to inventory
	public void addItem(Item item) {
		
		//loop through all items in inventory
		for(Item i : inventoryItems) {
			
			//if the items are the same (meaning we've already picked up similar item previously), add new count to the old count
			if(i.getId() == item.getId()) {
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		//otherwise, add the new item 
		inventoryItems.add(item);
		
	}

	/*************** GETTERS and SETTERS ***************/
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
		
}
