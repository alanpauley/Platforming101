package dev.apauley.general;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Responsible for starting game
 */

public class Launcher {

	public static void main(String[] args) {
		
		//Used for getting current Date (from here: https://www.javatpoint.com/java-get-current-date)
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy @ hh:mm:ss a");  
		Date date = new Date();
			    
		new Game(formatter.format(date) + " - Platforming 101", 600, 500);
	}
	
}