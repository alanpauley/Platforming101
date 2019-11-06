package dev.apauley.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * The base shell for all Entities in game
 */
public abstract class Entity {

	//Float = smoother movement using decimals for calculations
	//X and Y coordinates of entity
	protected float x, y;

	//Constructor to set Defaults
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
				
	}
	
	//Updates Entity
	public abstract void tick();
	
	//Draws Entity
	public abstract void render(Graphics g);

	/*************** GETTERS and SETTERS ***************/

}
