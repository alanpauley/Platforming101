package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class Tree extends Tile {

	public Tree(int id) {
		super(Assets.tree, id);
	}

	//Gets whether tile is Solid or not
	public boolean getIsSolid() {
		return true;
	}
	
}
