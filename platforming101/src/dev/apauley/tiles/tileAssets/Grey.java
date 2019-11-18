package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class Grey extends Tile {

	public Grey(int id) {
		super(Assets.grey, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
