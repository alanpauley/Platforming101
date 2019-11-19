package dev.apauley.states;

import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.ui.ClickListener;
import dev.apauley.ui.UIImageButton;
import dev.apauley.ui.UIManager;

/*
 * Where menu is at
 */

public class MenuState extends State {

	//Temp track positions
	private int x, y;
	
	//Handles UI elements
	//Whenever UIManager receives an event (mouse released, mouse moved, etc.),
	// It will take the current UIManager that is set (in this case the one created in menuState)
	// and it will pass along to it the events for these objects
	private UIManager uiManager;
	
	//Game Constructor
	public MenuState(final Handler handler) {

		//Calls the constructor of the State class and supplies game as the input parameter to THIS constructor
		super(handler);

		//Sets Mouse to default values
		x = handler.getGame().getMouseManager().getMouseX();
		y = handler.getGame().getMouseManager().getMouseY();

		//Manages the UI elements
		uiManager = new UIManager(handler);
		
		//Set the UIManager to the handler
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(handler.getGame().getWidth()/2 - 64,handler.getGame().getHeight()/2 - 32,128,64, Assets.btn_start, new ClickListener() {

			//When we click the start Button, what do we want to happen?
			@Override
			public void onClick() {
				
				//We set to null so that user cannot continue to click where the start button would be when in other states
				handler.getMouseManager().setUIManager(null);
				
				//Switches game to gameState
				State.setState(handler.getGame().gameState, "MenuState");
				
			}
		}));
		
	}
	
	@Override
	public void tick() {
		
		//Only Prints Mouse Coordinates if they have changed
		if(handler.getMouseManager().getMouseX() != x || handler.getMouseManager().getMouseY() != y) {
			x = handler.getMouseManager().getMouseX();
			y = handler.getMouseManager().getMouseY();
			//System.out.println("(" + handler.getMouseManager().getMouseX() + ", " + handler.getMouseManager().getMouseY() + ")"); //Debug
		}
		
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {		
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 4, 4);

		uiManager.render(g);
	}

}
