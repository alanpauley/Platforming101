package dev.apauley.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.gfx.Text;
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
	
	//Inventory screen position variables
	private int invX = 64, invY = 48
		      , invWidth = 512, invHeight = 384
			  , invListCenterX = invX + 171
			  , invListCenterY = invY + invHeight / 2 + 2
			  , invListSpacing = 30;
	
	//Inventory Image Positions
	private int invImageX = 452, invImageY = 82
			  , invImageWidth = 64, invImageHeight = 64; 
	
	//Inventory count positions
	private int invCountX = 484, invCountY = 172;
	
	//what item is selected by player (index of inventoryItems)
	private int selectedItem = 0;
	
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
		
		//Let's player navigate through inventory
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP))
			selectedItem--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN))
			selectedItem++;

		//Loops inventory from top to bottom and vice versa
		if(selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		if(selectedItem >= inventoryItems.size())
			selectedItem = 0;
	}
	
	public void render(Graphics g) {

		//Do not render unless inventory is active
		if(!active)
			return;
		
		//Draw inventoryScreen
		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
		
		//get count of items in inventory. If empty, exit code, otherwise continue		
		int len = inventoryItems.size();
		if(len == 0)
			return;
		
		//Display list of items
		//Note: We have 5 slots above and below center point, which is why the values below
		for(int i = -5; i < 6; i++) {
			
			//There may be cases where we don't have inventory large enough for the -5 through + 5, which is what below accounts for by skipping item
			if(selectedItem + i < 0 || selectedItem + i >= len)
				continue;
			
			//If targeting center item
			if(i == 0) {
				
				//Draw string to inventory
				Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX, invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);

			}else {
			
				//Draw string to inventory
				Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX, invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
			
			}
		}
		
		//Store selected item
		Item item = inventoryItems.get(selectedItem);
		
		//Draw selected item to screen
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
		
		//Draw count of selected item to screen
		Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);
		
	}
	
	/*************** INVENTORY METHODS ***************/
	
	//Adds item to inventory
	public void addItem(Item item) {
		
		//loop through all items in inventory and stacks them together if already exists
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
		
	public boolean isActive() {
		return active;
	}

}
