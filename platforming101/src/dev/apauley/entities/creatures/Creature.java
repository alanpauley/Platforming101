package dev.apauley.entities.creatures;

import dev.apauley.entities.Entity;

/*
 * The base shell for all Creatures in game
 */
public abstract class Creature extends Entity {

	//Tracks how much HP and speed creature has
	protected int health;

	//Creature Constructor. Establishes some defaults
	public Creature(float x, float y) {
		super(x,y);
		health = 10;
	}
}
