package dev.apauley.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.gfx.Text;

/*
 * Handles all debugging
 */

public class DebugManager {

	private Handler handler;
	
	private Font fontHeader = Assets.fontRobotoRegular40;
	private Font fontStats = Assets.fontRobotoRegular30;
	private Color color =  new Color(245,66,149);
	
	//spacing buffer
	private int spBf = 5;
	
	//Bounding box light up size
	private int bbox = 5;
	
	/*PLAYER STATS*/
	private int playerHeadX = spBf				, playerHeadY = fontHeader.getSize() - spBf
			  , playerStat1X = playerHeadX		, playerStat1Y = playerHeadY + fontStats.getSize() * 1 + spBf
			  , playerStat2X = playerHeadX		, playerStat2Y = playerHeadY + fontStats.getSize() * 2 + spBf
	  		  , playerStat3X = playerHeadX		, playerStat3Y = playerHeadY + fontStats.getSize() * 3 + spBf
	  		  , playerStat4X = playerHeadX		, playerStat4Y = playerHeadY + fontStats.getSize() * 4 + spBf
	  		  , playerStat5X = playerHeadX		, playerStat5Y = playerHeadY + fontStats.getSize() * 5 + spBf
			  , playerStat6X = playerHeadX		, playerStat6Y = playerHeadY + fontStats.getSize() * 6 + spBf
			  , playerStat7X = playerHeadX		, playerStat7Y = playerHeadY + fontStats.getSize() * 7 + spBf
			  , playerStat8X = playerHeadX		, playerStat8Y = playerHeadY + fontStats.getSize() * 8 + spBf
			  , playerStat9X = playerHeadX		, playerStat9Y = playerHeadY + fontStats.getSize() * 9 + spBf
			  , playerStat10X = playerHeadX		, playerStat10Y = playerHeadY + fontStats.getSize() * 10 + spBf
			  , playerStat11X = playerHeadX		, playerStat11Y = playerHeadY + fontStats.getSize() * 11 + spBf;
  		
	public DebugManager(Handler handler) {
		this.handler = handler;
	}
	
	public void Render(Graphics g) {

		//Draw player Bounding Box
		g.setColor(Color.GREEN);
		g.fillRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getBounds().x - handler.getGameCamera().getxOffset())
				 , (int) (handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getBounds().y - handler.getGameCamera().getyOffset())
				 , handler.getWorld().getEntityManager().getPlayer().getBounds().width, handler.getWorld().getEntityManager().getPlayer().getBounds().height);
		
		//Light up Bounding Box with collision
		
		g.setColor(Color.YELLOW);
		if(handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileTop())
			g.fillRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getBounds().y - handler.getGameCamera().getyOffset())
					 , handler.getWorld().getEntityManager().getPlayer().getBounds().width, bbox);
		if(handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileBottom())
			g.fillRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getBounds().y - handler.getGameCamera().getyOffset() + handler.getWorld().getEntityManager().getPlayer().getBounds().height - bbox)
					 , handler.getWorld().getEntityManager().getPlayer().getBounds().width, bbox);
		if(handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileLeft())
			g.fillRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getBounds().x - handler.getGameCamera().getxOffset())
					 , (int) (handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getBounds().y - handler.getGameCamera().getyOffset())
					 , bbox, handler.getWorld().getEntityManager().getPlayer().getBounds().height);
		if(handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileRight())
			g.fillRect((int) (handler.getWorld().getEntityManager().getPlayer().getX() + handler.getWorld().getEntityManager().getPlayer().getBounds().x - handler.getGameCamera().getxOffset() + handler.getWorld().getEntityManager().getPlayer().getBounds().width - bbox)
					 , (int) (handler.getWorld().getEntityManager().getPlayer().getY() + handler.getWorld().getEntityManager().getPlayer().getBounds().y - handler.getGameCamera().getyOffset())
					 , bbox, handler.getWorld().getEntityManager().getPlayer().getBounds().height);
		
		
		//Draw Player Debug to Screen
		Text.drawStringShadow(g, "Player", playerHeadX, playerHeadY, false, color, fontHeader);
		Text.drawStringShadow(g, "X: " + handler.getWorld().getEntityManager().getPlayer().getX(), playerStat1X, playerStat1Y, false, color, fontStats);
		Text.drawStringShadow(g, "Y: " + handler.getWorld().getEntityManager().getPlayer().getY(), playerStat2X, playerStat2Y, false, color, fontStats);
		Text.drawStringShadow(g, "MoveX: " + handler.getWorld().getEntityManager().getPlayer().getxMove(), playerStat3X, playerStat3Y, false, color, fontStats);
		Text.drawStringShadow(g, "MoveY: " + handler.getWorld().getEntityManager().getPlayer().getyMove(), playerStat4X, playerStat4Y, false, color, fontStats);
		Text.drawStringShadow(g, "Hangtime: " + handler.getWorld().getEntityManager().getPlayer().isHangtime(), playerStat5X, playerStat5Y, false, color, fontStats);
		Text.drawStringShadow(g, "T-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileTop(), playerStat6X, playerStat6Y, false, color, fontStats);
		Text.drawStringShadow(g, "B-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileBottom(), playerStat7X, playerStat7Y, false, color, fontStats);
		Text.drawStringShadow(g, "L-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileLeft(), playerStat8X, playerStat8Y, false, color, fontStats);
		Text.drawStringShadow(g, "R-Collision: " + handler.getWorld().getEntityManager().getPlayer().isCollisionWithTileRight(), playerStat9X, playerStat9Y, false, color, fontStats);
		Text.drawStringShadow(g, "EX-Collision: " + handler.getWorld().getEntityManager().getPlayer().checkEntityCollisions(handler.getWorld().getEntityManager().getPlayer().getxMove(), 0), playerStat10X, playerStat10Y, false, color, fontStats);
		Text.drawStringShadow(g, "EY-Collision: " + handler.getWorld().getEntityManager().getPlayer().checkEntityCollisions(0, handler.getWorld().getEntityManager().getPlayer().getyMove()), playerStat11X, playerStat11Y, false, color, fontStats);
		
	}
		
}
