package dev.apauley.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
 * Tracks the UI Image Button (opposed to other buttons which may just be text, etc.)
 */

public class UIImageButton extends UIObject {

	//Image at index 0 = image at rest, image at index 1 = hovered over image
	private BufferedImage[] images;
	
	private ClickListener clicker;
	
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {

		//If hovering draw hovered over image, otherwise draw image at rest
		if(hovering)
			g.drawImage(images[1], (int) x,  (int) y,  width,  height, null);
		else
			g.drawImage(images[0], (int) x,  (int) y,  width,  height, null);
	}

	
	//Whenever this button is clicked, perform some action
	@Override
	public void onClick() {
		
		//We could have this be abstract class and do separate method for all buttons, but ClickListener streamlines this
		//The clicklistener knows what object is being interacted with and only performs action based on that object
		clicker.onClick(); //9:40 for explanation (30 - Simple UI)
	}

}
