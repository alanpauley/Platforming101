package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class Rock extends Tile {

	public Rock(int id) {
		super(Assets.rock, id);
	}

	//Gets whether tile is Solid or not
	public boolean isSolid() {
		return true;
	}
	
}
