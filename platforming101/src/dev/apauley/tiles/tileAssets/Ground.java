package dev.apauley.tiles.tileAssets;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class Ground extends Tile {

	public Ground(int id) {
		super(Assets.ground, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
