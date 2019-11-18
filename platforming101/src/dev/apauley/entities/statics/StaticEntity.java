package dev.apauley.entities.statics;

import dev.apauley.entities.Entity;
import dev.apauley.general.Handler;

/*
 * Entities that do not move (tree/rock/etc.), opposed to those that do (creatures: Player, enemies, etc.)
 */

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height, String name) {
		super(handler, x, y, width, height, name);
	}
	
}
