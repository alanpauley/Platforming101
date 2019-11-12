package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;

/*
 * Where menu is at
 */

public class MenuState extends State {

	//Temp track positions
	private int x, y;
	
	//Game Constructor
	public MenuState(Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);

		//Sets Mouse to default values
		x = handler.getGame().getMouseManager().getMouseX();
		y = handler.getGame().getMouseManager().getMouseY();

	}
	
	@Override
	public void tick() {
		
		//Only Prints Mouse Coordinates if they have changed
		if(handler.getMouseManager().getMouseX() != x || handler.getMouseManager().getMouseY() != y) {
			x = handler.getMouseManager().getMouseX();
			y = handler.getMouseManager().getMouseY();
			System.out.println("(" + handler.getMouseManager().getMouseX() + ", " + handler.getMouseManager().getMouseY() + ")");
		}
		if(handler.getMouseManager().isLeftPressed() && handler.getMouseManager().isRightPressed())
			State.setState(handler.getGame().gameState);		
	}

	@Override
	public void render(Graphics g) {		
		g.drawImage(Assets.grass,5,0,null);
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
	}

}
