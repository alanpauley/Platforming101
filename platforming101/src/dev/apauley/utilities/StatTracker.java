package dev.apauley.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.apauley.general.Handler;
import dev.apauley.gfx.Assets;
import dev.apauley.gfx.Text;

/*
 * Tracks stats (SO many stats
 */

public class StatTracker {

	private Handler handler;
	
	//fonts
	private Font fontHeader = Assets.fontRobotoRegular30
			   , fontBody = Assets.fontRobotoRegular20;

	//Tracks enemies counts
	//Future Idea: convert to map or whatever so can track how many per type in an array/map
	private int enemiesSeen = 0
			  , enemiesFought = 0
			  , enemiesAvoided = 0
			  , enemiesKilled = 0;

	//Tracks how many bullets fired
	//Future Idea: convert to map or whatever so can track how many per type in an array/map
	private int bulletsFiredPlayer = 0
			  , bulletsFiredEnemies = 0;

	//Tracks how many times done each action below
	private int jumpCountPlayer = 0
			  , runCountPlayer = 0
			  , reloadCountPlayer = 0
			  , faceTopCountPlayer = 0
			  , faceBottomCountPlayer = 0
			  , faceLeftCountPlayer = 0
			  , faceRightCountPlayer = 0
			  , pauseCount = 0
			  , checkInventoryCount = 0
			  , startGameCount = 0
			  , continueGameCount = 0;
	
	private float playerWalkDistance = 0
				, playerRunDistance = 0
				, jumpDistanceX = 0
				, jumpDistanceY = 0
				, fallDistanceX = 0
				, fallDistanceY = 0
				, avgSpeed = 0;
	
	//Tracks player stats
	//Future HealthGained
	private int hitCountPlayer = 0 //# of times a player is Hit
			  , hitCountEnemies = 0
			  , healthLostPlayer = 0
			  , healthLostEnemies = 0
			  , deathCount = 0;	
	
	//Tracks how long player has done certain things
	private float timeGrounded = 0 //Time spent on ground
			    , timeAirbourne = 0
			    , timeWalking = 0
			    , timeRunning = 0
			    , timeFacingUp = 0
			    , timeFacingDown = 0
			    , timeFacingLeft = 0
			    , timeFacingRight = 0;
	
	//Tracks how long player has played
	//Total = since boot up
	//Game = playing (NOT paused)
	//Future: Menu, paused, etc.
	private float totalTimePlayed = 0
			    , gameTimePlayed = 0;
	
	public StatTracker(Handler handler) {
		this.handler = handler;
	}
	
	public void render(Graphics g) {
		int x = 20; //Padding/intent from edge of screen
		int y = 25;
		int z = 3; //Border size
		
		int t = 5; //Text Offset
		int i = 1; //Each new line multiplier
		int j = 2; //New line spacing
		
		Color border = new Color(50,50,50,230);
		Color mainFill = new Color(0,0,0,200);
		Color headerFill = new Color(0,0,0,255);
		Color bodyFill = new Color(255,255,255,255);
		Color headerText = new Color(255,255,255,255);
		Color bodyText = new Color(0,0,0,255);
		
		g.setColor(border);
		g.fillRect(x, y, handler.getGame().getWidth() - x * 2, handler.getGame().getHeight() - y * 2);
		g.setColor(mainFill);
		g.fillRect(x + z, y + z, handler.getGame().getWidth() - (x + z) * 2, handler.getGame().getHeight() - (y + z) * 2);
		
		g.setColor(headerFill);
		g.fillRect(x + t, y, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Enemies", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Enemies Seen: " + enemiesSeen, x + t, y + (fontBody.getSize() + t * j) * i , false, bodyText, fontBody); i++;
			Text.drawString(g, "Enemies Fought: " + enemiesFought, x + t, y + (fontBody.getSize() + t + j) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Enemies Avoided: " + enemiesAvoided, x + t, y + (fontBody.getSize() + t + j) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Enemies Killed: " + enemiesKilled, x + t, y + (fontBody.getSize() + t + j) * i, false, bodyText, fontBody); i++;

		g.setColor(headerFill);
		g.fillRect(x + t, y + 100*2, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Shots", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + 100*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Shots Fired (Player): " + bulletsFiredPlayer, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Shots Fired (Enemies): " + bulletsFiredEnemies, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;

		g.setColor(headerFill);
		g.fillRect(x + t, y + 200*2, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Action Count", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + 200*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Jumped: " + jumpCountPlayer, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Ran: " + runCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Reloaded: " + reloadCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Faced (Up): " + faceTopCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Faced (Down): " + faceBottomCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Faced (Left): " + faceLeftCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Faced (Right): " + faceRightCountPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Paused: " + pauseCount, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Check Inventory: " + checkInventoryCount, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Start Game: " + startGameCount, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Continue Game: " + continueGameCount, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;

		g.setColor(headerFill);
		g.fillRect(x + t, y + 300*2, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Travel Distance", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + 300*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Walked: " + playerWalkDistance, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Ran: " + playerRunDistance, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Jumped (x): " + jumpDistanceX, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Jumped (y): " + jumpDistanceY, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Fell (x): " + fallDistanceX, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Fell (y): " + fallDistanceY, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Average Speed: " + avgSpeed, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
	
		x = 400; //Padding/intent from edge of screen
		i = 1; //Each new line multiplier
		
		g.setColor(headerFill);
		g.fillRect(x + t, y, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Damage", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Times Hit (Player): " + hitCountPlayer, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Times Hit (Enemies): " + hitCountEnemies, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Health Lost (Player): " + healthLostPlayer, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Health Lost (Enemies): " + healthLostEnemies, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Death Count: " + deathCount, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;

		g.setColor(headerFill);
		g.fillRect(x + t, y + 100*2, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Time Spent", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + 100*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Grounded: " + timeGrounded, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Airborne: " + timeAirbourne, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Walking: " + timeWalking, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Running: " + timeRunning, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Facing (Up): " + timeFacingUp, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Facing (Down): " + timeFacingDown, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Facing (Left): " + timeFacingLeft, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Facing (Right): " + timeFacingRight, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
		
		g.setColor(headerFill);
		g.fillRect(x + t, y + 200*2, 250, fontHeader.getSize());
			Text.drawStringShadow(g, "Time Played", x + t, y + (fontHeader.getSize() + t) * i, false, headerText, fontHeader); i++;
		g.setColor(bodyFill);
		g.fillRect(x + t, y + 200*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
			Text.drawString(g, "Total Time: " + totalTimePlayed, x + t, y + (fontBody.getSize() + t + j * 4) * i, false, bodyText, fontBody); i++;
			Text.drawString(g, "Game: " + gameTimePlayed, x + t, y + (fontBody.getSize() + t + j + 4) * i, false, bodyText, fontBody); i++;
	
	}

	/*************** GETTERS and SETTERS ***************/	
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getEnemiesSeen() {
		return enemiesSeen;
	}

	public void setEnemiesSeen(int enemiesSeen) {
		this.enemiesSeen = enemiesSeen;
	}

	public void increaseEnemiesSeen(int add) {
		this.enemiesSeen += add;
	}

	public int getEnemiesFought() {
		return enemiesFought;
	}

	public void setEnemiesFought(int enemiesFought) {
		this.enemiesFought = enemiesFought;
	}

	public void increaseEnemiesFought(int add) {
		this.enemiesFought += add;
	}

	public int getEnemiesAvoided() {
		return enemiesAvoided;
	}

	public void setEnemiesAvoided(int enemiesAvoided) {
		this.enemiesAvoided = enemiesAvoided;
	}

	public void increaseEnemiesAvoided(int add) {
		this.enemiesAvoided += add;
	}

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	public void increaseEnemiesKilled(int add) {
		this.enemiesKilled += add;
	}

	public int getBulletsFiredPlayer() {
		return bulletsFiredPlayer;
	}

	public void setBulletsFiredPlayer(int bulletsFiredPlayer) {
		this.bulletsFiredPlayer = bulletsFiredPlayer;
	}

	public int getBulletsFiredEnemies() {
		return bulletsFiredEnemies;
	}

	public void setBulletsFiredEnemies(int bulletsFiredEnemies) {
		this.bulletsFiredEnemies = bulletsFiredEnemies;
	}

	public int getJumpCountPlayer() {
		return jumpCountPlayer;
	}

	public void setJumpCountPlayer(int jumpCountPlayer) {
		this.jumpCountPlayer = jumpCountPlayer;
	}

	public int getRunCountPlayer() {
		return runCountPlayer;
	}

	public void setRunCountPlayer(int runCountPlayer) {
		this.runCountPlayer = runCountPlayer;
	}

	public int getReloadCountPlayer() {
		return reloadCountPlayer;
	}

	public void setReloadCountPlayer(int reloadCountPlayer) {
		this.reloadCountPlayer = reloadCountPlayer;
	}

	public int getFaceTopCountPlayer() {
		return faceTopCountPlayer;
	}

	public void setFaceTopCountPlayer(int faceTopCountPlayer) {
		this.faceTopCountPlayer = faceTopCountPlayer;
	}

	public int getFaceBottomCountPlayer() {
		return faceBottomCountPlayer;
	}

	public void setFaceBottomCountPlayer(int faceBottomCountPlayer) {
		this.faceBottomCountPlayer = faceBottomCountPlayer;
	}

	public int getFaceLeftCountPlayer() {
		return faceLeftCountPlayer;
	}

	public void setFaceLeftCountPlayer(int faceLeftCountPlayer) {
		this.faceLeftCountPlayer = faceLeftCountPlayer;
	}

	public int getFaceRightCountPlayer() {
		return faceRightCountPlayer;
	}

	public void setFaceRightCountPlayer(int faceRightCountPlayer) {
		this.faceRightCountPlayer = faceRightCountPlayer;
	}

	public int getPauseCount() {
		return pauseCount;
	}

	public void setPauseCount(int pauseCount) {
		this.pauseCount = pauseCount;
	}

	public int getCheckInventoryCount() {
		return checkInventoryCount;
	}

	public void setCheckInventoryCount(int checkInventoryCount) {
		this.checkInventoryCount = checkInventoryCount;
	}

	public int getStartGameCount() {
		return startGameCount;
	}

	public void setStartGameCount(int startGameCount) {
		this.startGameCount = startGameCount;
	}

	public int getContinueGameCount() {
		return continueGameCount;
	}

	public void setContinueGameCount(int continueGameCount) {
		this.continueGameCount = continueGameCount;
	}

	public float getPlayerRunDisance() {
		return playerRunDistance;
	}

	public void setPlayerRunDisance(float playerRunDisance) {
		this.playerRunDistance = playerRunDisance;
	}

	public float getPlayerWalkDistance() {
		return playerWalkDistance;
	}

	public void setPlayerWalkDistance(float playerWalkDistance) {
		this.playerWalkDistance = playerWalkDistance;
	}

	public float getJumpDistanceX() {
		return jumpDistanceX;
	}

	public void setJumpDistanceX(float jumpDistanceX) {
		this.jumpDistanceX = jumpDistanceX;
	}

	public float getJumpDistanceY() {
		return jumpDistanceY;
	}

	public void setJumpDistanceY(float jumpDistanceY) {
		this.jumpDistanceY = jumpDistanceY;
	}

	public float getFallDistanceX() {
		return fallDistanceX;
	}

	public void setFallDistanceX(float fallDistanceX) {
		this.fallDistanceX = fallDistanceX;
	}

	public float getFallDistanceY() {
		return fallDistanceY;
	}

	public void setFallDistanceY(float fallDistanceY) {
		this.fallDistanceY = fallDistanceY;
	}

	public float getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(float avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public int getHitCountPlayer() {
		return hitCountPlayer;
	}

	public void setHitCountPlayer(int hitCountPlayer) {
		this.hitCountPlayer = hitCountPlayer;
	}

	public int getHitCountEnemies() {
		return hitCountEnemies;
	}

	public void setHitCountEnemies(int hitCountEnemies) {
		this.hitCountEnemies = hitCountEnemies;
	}

	public int getHealthLostPlayer() {
		return healthLostPlayer;
	}

	public void setHealthLostPlayer(int healthLostPlayer) {
		this.healthLostPlayer = healthLostPlayer;
	}

	public int getHealthLostEnemies() {
		return healthLostEnemies;
	}

	public void setHealthLostEnemies(int healthLostEnemies) {
		this.healthLostEnemies = healthLostEnemies;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}

	public float getTimeGrounded() {
		return timeGrounded;
	}

	public void setTimeGrounded(float timeGrounded) {
		this.timeGrounded = timeGrounded;
	}

	public float getTimeAirbourne() {
		return timeAirbourne;
	}

	public void setTimeAirbourne(float timeAirbourne) {
		this.timeAirbourne = timeAirbourne;
	}

	public float getTimeRunning() {
		return timeRunning;
	}

	public void setTimeRunning(float timeRunning) {
		this.timeRunning = timeRunning;
	}

	public float getTimeWalking() {
		return timeWalking;
	}

	public void setTimeWalking(float timeWalking) {
		this.timeWalking = timeWalking;
	}

	public float getTimeFacingUp() {
		return timeFacingUp;
	}

	public void setTimeFacingUp(float timeFacingUp) {
		this.timeFacingUp = timeFacingUp;
	}

	public float getTimeFacingDown() {
		return timeFacingDown;
	}

	public void setTimeFacingDown(float timeFacingDown) {
		this.timeFacingDown = timeFacingDown;
	}

	public float getTimeFacingLeft() {
		return timeFacingLeft;
	}

	public void setTimeFacingLeft(float timeFacingLeft) {
		this.timeFacingLeft = timeFacingLeft;
	}

	public float getTimeFacingRight() {
		return timeFacingRight;
	}

	public void setTimeFacingRight(float timeFacingRight) {
		this.timeFacingRight = timeFacingRight;
	}

	public float getTotalTimePlayed() {
		return totalTimePlayed;
	}

	public void setTotalTimePlayed(float totalTimePlayed) {
		this.totalTimePlayed = totalTimePlayed;
	}

	public float getGameTimePlayed() {
		return gameTimePlayed;
	}

	public void setGameTimePlayed(float gameTimePlayed) {
		this.gameTimePlayed = gameTimePlayed;
	}

}
