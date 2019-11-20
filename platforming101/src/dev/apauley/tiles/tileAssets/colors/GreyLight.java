package dev.apauley.tiles.tileAssets.colors;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class GreyLight extends Tile {

	public GreyLight(int id) {
		super(Assets.greyLight, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
