package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class GroundBevel extends Tile {

	public GroundBevel(int id) {
		super(Assets.groundBevel, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
