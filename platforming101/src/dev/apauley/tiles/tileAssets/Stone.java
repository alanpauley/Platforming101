package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class Stone extends Tile {

	public Stone(int id) {
		super(Assets.stone, id);
	}

	//Gets whether tile is Solid or not
	public boolean isSolid() {
		return true;
	}
	
}
