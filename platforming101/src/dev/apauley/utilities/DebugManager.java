package dev.apauley.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.apauley.entities.Entity;
import dev.apauley.entities.creatures.Enemies;
import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.gfx.Text;

/*
 * Handles all debugging
 */

public class DebugManager {

	private Handler handler;
	
	private Font fontHeader = Assets.fontRobotoRegular30;
	private Font fontStats = Assets.fontRobotoRegular20;
	
	//Sets transparency for BG rectangles
	private int alpha = 50;
	
	private boolean debugSystem, debugPlayer, debugObjects, debugRandom, debugBoundingBox;
	
	//spacing buffer
	private int spBf = 5;
	
	//Bounding box light up size
	private int bbox = 5;
	
	/*STAT HEADER POSITIONS*/
	private int headYTopPreStats = fontHeader.getSize() - spBf - 90
			  , headYTopPostStats = headYTopPreStats + 90
			  , headYTop = headYTopPreStats
			  , headYBottom = fontHeader.getSize() - spBf + 780;
			  
  		
	public DebugManager(Handler handler) {
		this.handler = handler;
	}
	
	//Draw Bounding box for creatures in your specified color for each side you list as TRUE
	public void drawBoundingBox(Graphics g, Entity e, boolean top, boolean bottom, boolean left, boolean right, Color c) {

		g.setColor(c);
		
		if(top)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset())
					 , e.getBounds().width, bbox);
		if(bottom)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset() + e.getBounds().height - bbox)
					 , e.getBounds().width, bbox);
		if(left)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset())
					 , bbox, e.getBounds().height);
		if(right)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset() + e.getBounds().width - bbox)
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset())
					 , bbox, e.getBounds().height);
	}

	//Draw smaller facing box in your specified color for each side you list as TRUE
	public void drawFacingBox(Graphics g, Entity e, boolean top, boolean bottom, boolean left, boolean right, Color c) {

		g.setColor(c);
		
		if(top)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset() + bbox)
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset() + bbox)
					 , e.getBounds().width - bbox * 2, bbox * 2);
		if(bottom)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset() + bbox)
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset() + e.getBounds().height - bbox - bbox * 2)
					 , e.getBounds().width - bbox * 2, bbox * 2);
		if(left)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset() + bbox)
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset() + bbox)
					 , bbox * 2, e.getBounds().height - bbox * 2);
		if(right)
			g.fillRect((int) (e.getX() + e.getBounds().x - handler.getGameCamera().getxOffset() + e.getBounds().width - bbox - bbox * 2)
					 , (int) (e.getY() + e.getBounds().y - handler.getGameCamera().getyOffset() + bbox)
					 , bbox * 2, e.getBounds().height - bbox * 2);
	}

	//Takes in the horizontal int and converts it based on input parameters
	public int getStX(int mult) {
		return mult;
	}

	//Takes in the vertical int and converts it based on input parameters
	public int getStY(int mult, String tb) {
		if(tb == "T") //Top
			return headYTop + fontStats.getSize() * mult + spBf;
		if(tb == "B") //Bottom
			return headYBottom + fontStats.getSize() * mult + spBf;
		return -1; //Error
	}
	
	public void tick() {
		
		//Set headYTop based on currentPhase
		if(handler.getPhaseManager().getCurrentPhase() <= 15)
			headYTop = headYTopPreStats;
		if(handler.getPhaseManager().getCurrentPhase() > 15)
			headYTop = headYTopPostStats;
			
		//Create enemies
		if(handler.getKeyManager().debugTrigger && handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)) 
			handler.getWorld().getEntityManager().getEntitiesLimbo().add(new Enemies(handler, 400,400, 0f, 0f));

		if(handler.getKeyManager().speedDown && handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)) {
			handler.getWorld().getEntityManager().speedDown();
		}
		if(handler.getKeyManager().speedUp && handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
			handler.getWorld().getEntityManager().speedUp();
		}
	}	

	public void render(Graphics g) {
		
		if(handler.getPhaseManager().getCurrentPhase() > 13)
			headYTop = headYTop + 90;

		//Only Draw if DebugBoundingBox = True
		if(debugBoundingBox) {
		
			//Draw Bounding boxes around all entities
			for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
				
				//Get entity condensed name
				String name;
				
				
				//Get color(s) based on entity
				Color c1 = Color.BLACK
					, c2 = Color.WHITE
					, c3 = Color.RED;
				if(e.getName().equals("PLAYER")) {
					c1 = Color.GREEN;
				    c2 = Color.YELLOW;
				    c3 = Color.CYAN;
				} else if(e.getName().equals("BULLET")) {
					c1 = Color.PINK;
				    c2 = Color.ORANGE;
				    c3 = Color.YELLOW;
				} else if(e.getName().equals("ENEMY")) {
					c1 = Color.BLUE;
				    c2 = Color.WHITE;
				    c3 = Color.RED;
				}
				
				//Draw Bounding Box
				drawBoundingBox(g, e, true, true, true, true, c1);

				//Light up Bounding Box with Tile collision
				drawBoundingBox(g, e, e.isCollisionWithTileTop()
					      , e.isCollisionWithTileBottom()
					      , e.isCollisionWithTileLeft()
					      , e.isCollisionWithTileRight()
					      , c2);
	
				//Light up Smaller Box with direction facing
				drawFacingBox(g, e, e.isFaceTop()
						      , e.isFaceBottom()
						      , e.isFaceLeft()
						      , e.isFaceRight()
						      , c3);
			}

		}

		//Only Draw if DebugSystem = True
		if(debugSystem) {
			
			//Draw System Debug to Screen
			Color color =  new Color(247,95,30); //Orange
			int x = 2, i = 1;

			//Draw Transparent BG Rectangle
			g.setColor(new Color(247,95,30,alpha));
			g.fillRect(x-5, headYTop - 25, handler.getPhaseManager().getCurrentPhaseNameLength(), 32 + 21 * 5); //getCurrentPhaseNameLength() is used to keep this variable based on name

			//Draw Text to screen
			Text.drawStringShadow(g, "System", x, headYTop, false, color, fontHeader);
			Text.drawStringShadow(g, "Phase: " + handler.getPhaseManager().getCurrentPhase() + " - " + handler.getPhaseManager().getCurrentPhaseName(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Width: " + handler.getGame().getWidth(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Height: " + handler.getGame().getHeight(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "FPS: " + handler.getGame().getFpsTicks(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "State: " + handler.getGame().getStateName(), x, getStY(i,"T"), false, color, fontStats); i++;
		}

		//Only Draw if DebugObjects = True
		if(debugObjects) {
			
			//Draw Objects Debug to Screen
			Color color =  new Color(121,0,227); //Purple
			int x = 552, i = 1;

			//Draw Transparent BG Rectangle
			g.setColor(new Color(121,0,227,alpha));
			int enemyCount = handler.getWorld().getEntityManager().getEnemyCount();
			int baseHeight = 32 + 21 * 5;
			int height = baseHeight;
			
			//Adjust height if there are enemies
			if(enemyCount > 0)
				height = height + 32 + 21 * enemyCount;
			
			g.fillRect(x-5, headYTop - 25, 198, height);

			//Draw Text to screen
			Text.drawStringShadow(g, "Objects", x, headYTop, false, color, fontHeader);
			Text.drawStringShadow(g, "Entity #: " + handler.getWorld().getEntityManager().getEntities().size(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Player #: " + handler.getWorld().getEntityManager().getPlayerCount(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Enemy #: " + handler.getWorld().getEntityManager().getEnemyCount(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "(Player) Bullet #: " + handler.getWorld().getEntityManager().getBulletPlayerCount(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "(Enemy) Bullet #: " + handler.getWorld().getEntityManager().getBulletEnemyCount(), x, getStY(i,"T"), false, color, fontStats); i++;
			
			//if Enemies exist, drawing, otherwise break
			if(enemyCount > 0) {
				Text.drawStringShadow(g, "Enemies", x, headYTop + baseHeight, false, color, fontHeader);
				int z = 1;
				for(Entity e : handler.getWorld().getEntityManager().getEntities())
					if(e.getName().equals("ENEMY")) {
						//tbh, no idea why this math works, feels like 1 * z should not... Havne't researched function like I should. Putting off for now.
						Text.drawStringShadow(g, "Enemy" + e.getId() + ": HP: " + e.getHealth(), x, getStY(i,"T") + 32 + 1 * z, false, color, fontStats); i++; 
						z++;
					}
			}
		}
		
		//Only Draw if DebugPlayer = True
		if(debugPlayer) {
			
			//Draw Player Debug to Screen
			Color color =  new Color(245,66,149); //Pink
			int x = 750, i = 1;

			//Draw Transparent BG Rectangle
			g.setColor(new Color(245,66,149,alpha));
			g.fillRect(x-5, headYTop - 25, 180, 32 + 21 * 11);

			//Draw Text to screen
			Text.drawStringShadow(g, "Player", x, headYTop, false, color, fontHeader);
			Text.drawStringShadow(g, "X: " + handler.getWorld().getEntityManager().getPlayer().getX(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Y: " + handler.getWorld().getEntityManager().getPlayer().getY(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "MoveX: " + handler.getWorld().getEntityManager().getPlayer().getxMove(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "MoveY: " + handler.getWorld().getEntityManager().getPlayer().getyMove(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Speed: " + handler.getWorld().getEntityManager().getPlayer().getSpeed(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Running: " + handler.getWorld().getEntityManager().getPlayer().isRunning(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Jumping: " + handler.getWorld().getEntityManager().getPlayer().isJumping(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Hangtime: " + handler.getWorld().getEntityManager().getPlayer().isHangtime(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "Can Jump: " + handler.getWorld().getEntityManager().getPlayer().isCanJump(), x, getStY(i,"T"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "HP: " + handler.getWorld().getEntityManager().getPlayer().getHealth(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "emptyGunTimer: " + handler.getWorld().getEntityManager().getPlayer().getEmptyGunTimer(), x - 50, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "EX-Collision: " + handler.getWorld().getEntityManager().getPlayer().checkEntityCollisions(handler.getWorld().getEntityManager().getPlayer().getxMove(), 0), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "EY-Collision: " + handler.getWorld().getEntityManager().getPlayer().checkEntityCollisions(0, handler.getWorld().getEntityManager().getPlayer().getyMove()), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "T-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileTop(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "B-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileBottom(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "L-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileLeft(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "R-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileRight(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "T-Face: " + handler.getWorld().getEntityManager().getPlayer().isFaceTop(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "B-Face: " + handler.getWorld().getEntityManager().getPlayer().isFaceBottom(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "L-Face: " + handler.getWorld().getEntityManager().getPlayer().isFaceLeft(), x, getStY(i,"T"), false, color, fontStats); i++;
//			Text.drawStringShadow(g, "R-Face: " + handler.getWorld().getEntityManager().getPlayer().isFaceRight(), x, getStY(i,"T"), false, color, fontStats); i++;		
		}
		
		//Only Draw if DebugRandom = True
		if(debugRandom) {
			
			//Draw System Debug to Screen
			Color color =  new Color(102,102,255); //Purplish Blue
			int x = 2, i = 1;

			//Draw Transparent BG Rectangle
			g.setColor(new Color(102,102,255,alpha));
			g.fillRect(x-5, headYBottom - 28, 185, 32 + 21 * 4 + 20); //Added +20 cause it doesn't really matter and want to fill to bottom (off screen is okay)

			//Draw Text to screen
			Text.drawStringShadow(g, "Random", x, headYBottom, false, color, fontHeader);
			Text.drawStringShadow(g, "xOffset: " + handler.getGameCamera().getxOffset(), x, getStY(i,"B"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "yOffset: " + handler.getGameCamera().getyOffset(), x, getStY(i,"B"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "xMouse: " + handler.getMouseManager().getMouseX(), x, getStY(i,"B"), false, color, fontStats); i++;
			Text.drawStringShadow(g, "yMouse: " + handler.getMouseManager().getMouseY(), x, getStY(i,"B"), false, color, fontStats); i++;
		}
		
	}
		
	/*************** GETTERS and SETTERS ***************/

	//set ALL debugs on/off
	public void setAllDebugs(boolean tf) {
		debugSystem = tf;
		debugPlayer = tf;
		debugObjects = tf;
		debugRandom = tf;
		debugBoundingBox = tf;
	}

	//set ALL debugs on/off
	public void toggleAllDebugs() {
		boolean tf = isDebugSystem();
		setAllDebugs(false);
		debugSystem = !tf;
		debugPlayer = !tf;
		debugObjects = !tf;
		debugRandom = !tf;
		debugBoundingBox = !tf;
	}

	//Checks whether debugSystem is true or false
	public boolean isDebugSystem() {
		return debugSystem;
	}

	//set debugSystem to true or false
	public void setDebugSystem(boolean debugSystem) {
		setAllDebugs(false);
		this.debugSystem = debugSystem;
	}

	//Toggles Debug System to opposite (on >> off, off >> on)
	public void toggleDebugSystem() {
		boolean tf = isDebugSystem();
		setAllDebugs(false);
		debugSystem = !tf;
	}
	
	//Checks whether debugPlayer is true or false
	public boolean isDebugPlayer() {
		return debugPlayer;
	}

	//set debugPlayer to true or false
	public void setDebugPlayer(boolean debugPlayer) {
		setAllDebugs(false);
		this.debugPlayer = debugPlayer;
	}

	//Toggles Debug Player to opposite (on >> off, off >> on)
	public void toggleDebugPlayer() {
		boolean tf = isDebugPlayer();
		setAllDebugs(false);
		debugPlayer = !tf;
	}
	
	//Checks whether debugObjects is true or false
	public boolean isDebugObjects() {
		return debugObjects;
	}

	//set debugObjects to true or false
	public void setDebugObjects(boolean debugObjects) {
		setAllDebugs(false);
		this.debugObjects = debugObjects;
	}

	//Toggles Debug Objects to opposite (on >> off, off >> on)
	public void toggleDebugObjects() {
		boolean tf = isDebugObjects();
		setAllDebugs(false);
		debugObjects= !tf;
	}
	
	//Checks whether debugRandom is true or false
	public boolean isDebugRandom() {
		return debugRandom;
	}

	//set debugRandom to true or false
	public void setDebugRandom(boolean debugRandom) {
		setAllDebugs(false);
		this.debugRandom = debugRandom;
	}

	//Toggles Debug Random to opposite (on >> off, off >> on)
	public void toggleDebugRandom() {
		boolean tf = isDebugRandom();
		setAllDebugs(false);
		debugRandom = !tf;
	}

	//Checks whether debugBoundingBox is true or false
	public boolean isDebugBoundingBox() {
		return debugBoundingBox;
	}

	//set debugBoundingBox to true or false
	public void setDebugBoundingBox(boolean debugBoundingBox) {
		setAllDebugs(false);
		this.debugBoundingBox = debugBoundingBox;
	}

	//Toggles Debug BoundingBox to opposite (on >> off, off >> on)
	public void toggleDebugBoundingBox() {
		boolean tf = isDebugBoundingBox();
		setAllDebugs(false);
		debugBoundingBox= !tf;
	}
	
	//Temp just for screen capture
	public void setDebugCapture() {
		debugSystem = true;
		debugPlayer = true;
		debugObjects = true;
		debugBoundingBox = true;
	}
	
}
