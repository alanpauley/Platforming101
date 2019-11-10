package dev.apauley.entities.statics;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

/*
 * Tree that our player will interact with.
 */

public class Tree extends StaticEntity {
	
	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree,(int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
