package dev.apauley.general;

import dev.apauley.gfx.GameCamera;
import dev.apauley.input.KeyManager;
import dev.apauley.worlds.World;

public class Handler {

	private Game game;
	
	//Level Array, which holds multiple individual levels
	private World world;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	/*************** GETTERS and SETTERS ***************/
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
}
