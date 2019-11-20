package dev.apauley.tiles.tileAssets.colors;

import dev.apauley.gfx.Assets;
import dev.apauley.tiles.Tile;

public class GreyDark extends Tile {

	public GreyDark(int id) {
		super(Assets.greyDark, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
