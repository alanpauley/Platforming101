package dev.apauley.display;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * Display window for game
 */

public class Display {

	private JFrame frame; //How we display window
	private Canvas canvas; //How we draw graphics on the frame
	
	private String title; //Window title
	private int width, height; //Width and height of window, in pixels
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay(); //Method to create GUI display
	}
	
	//Creates GUI display
	private void createDisplay() {
		
		//Set window attributes
		frame = new JFrame(title); 
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close window AND close down game/program
		frame.setResizable(false); //Not allowing resizing
		frame.setLocationRelativeTo(null); //When window first pops, do so at center
		frame.setVisible(true); //By default not visible, so making it visible
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height)); //Set preferred size of canvas	
		
		//Set Max and Min size of canvas - somehow this ensures static size that won't change
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		
		canvas.setFocusable(false); //Makes it so Application is only thing with focus? Lets us register key presses
		frame.add(canvas); //Adds frame to canvas
		frame.pack(); //Resize window so we can fully see canvas
	}
	
}
