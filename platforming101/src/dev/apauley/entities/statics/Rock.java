package dev.apauley.entities.statics;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.items.Item;
import dev.apauley.tiles.Tile;

/*
 * Tree that our player will interact with.
 */

public class Rock extends StaticEntity {
	
	public Rock(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT,"ROCK","ITEM");
		
		//Tree Bounding Box
		bounds.x = 3;
		bounds.y = (int) (height / 2f);
		bounds.width = width - 6;
		bounds.height = (int) (height - height/2f);
	}

	@Override
	public void tick() {}

	//The process that occurs when an entity dies
	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.rock.createNew((int) (x + Item.ITEM_WIDTH / 2), (int) (y + Item.ITEM_HEIGHT / 2)));
	}
	
	@Override
	public void render(Graphics g) {
		if(flash > 0)
			g.drawImage(Assets.fobj2,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		else 
			g.drawImage(Assets.obj2,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
