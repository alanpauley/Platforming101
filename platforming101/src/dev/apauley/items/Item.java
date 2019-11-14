package dev.apauley.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;

/*
 * item creation (both those in game, AND in inventory)
 */

public class Item {

	/*************** HANDLER ***************/

	public static Item[] items = new Item[256];
	public static Item wood = new Item(Assets.wood, "Wood", 0);
	public static Item rock = new Item(Assets.rock, "Rock", 1);
	
	/*************** CLASS ***************/
	
	public static final int ITEM_WIDTH = 32, ITEM_HEIGHT = ITEM_WIDTH;
	
	protected Handler handler;
	
	//The texture to display item
	protected BufferedImage texture;
	
	//Name of item to be displayed in inventory
	protected String name;

	//unique item id
	protected final int id;
	
	//Bounding rectangle for the item on the ground
	protected Rectangle bounds; 
	
	//Item position
	protected int x, y;
	
	//count stores amount of items
	protected int count;
	
	//tracks whether item is picked up or not
	protected boolean pickedUp = false;
	
	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;

		bounds = new Rectangle(x, y, ITEM_WIDTH, ITEM_HEIGHT);
		
		items[id] = this;
	}
	
	public void tick() {
		
		//If player collides with item on ground, pick it up
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			
			//add item to inventory
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
		
	}
	
	//Used to display items on the ground in the game world
	public void render(Graphics g) {
		
		//Check if handler is created yet to avoid errors
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}

	//Used to display items in inventory using specific coordinates
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture,  x,  y,  ITEM_WIDTH,  ITEM_HEIGHT,  null);
	}
	

	//Creates copy of item class that is currently here
	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}

	//Overloaded: generates an item instance and does not add to game world, but is set up to add to inventory (Testing purposes only?)
	public Item createNew(int count) {
		Item i = new Item(texture, name, id);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	//Sets an items position AND sets bounding box for item drop
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
		//Sets bounding box for item drop
		bounds.x = x;
		bounds.y = y;
	}
	
	/*************** GETTERS and SETTERS ***************/

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}
