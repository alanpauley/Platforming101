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
		int x = 20; //Padding/indent from edge of screen
		int y = 25;
		int z = 3; //Border size
		
		//Color border = new Color(50,50,50,230);
		//Color mainFill = new Color(0,0,0,200);
		Color border = new Color(0,255,0,255); //Temp test green
		Color mainFill = new Color(255,0,0,255); //Temp test red
		Color headerFill = new Color(0,0,0,255);
		Color bodyFill = new Color(255,255,255,255);
		Color headerText = new Color(255,255,255,255);
		Color bodyText = new Color(0,0,0,255);

//BOTH SIDES/NEUTRAL (B)
	//B0:BORDER
		g.setColor(border);
		g.fillRect(x, y, handler.getGame().getWidth() - x * 2, handler.getGame().getHeight() - y * 2);
		g.setColor(mainFill);
		g.fillRect(x + z, y + z, handler.getGame().getWidth() - (x + z) * 2, handler.getGame().getHeight() - (y + z) * 2);
		
//LEFT SIDE (L)
		x = 25; //Padding/indent from edge of screen
		y = 30;
		
		int x2 = x + 5; //Debug Purposes
		int xIdntHead = x + 5;
		int xIdntBody = x + 20;
		int yHead = y + fontHeader.getSize();
		int yBody = yHead + 6;
		int headHeight = fontHeader.getSize() + 10;
		int bodyHeight = fontBody.getSize() + 5;
		int bodyFillHeight = bodyHeight - 1;
		int bodyFillAdd = 2;
		int width = 250;
		int sHeight = 20; //new Section spacer height
		
		int hCnt = 0; //Header Counts
		int sCnt = 0; //new Section Counts
		int iCnt = 0; //Each new line multiplier
		
	//L1:ENEMIES
		g.setColor(headerFill);
		g.fillRect(x, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, headHeight);
			Text.drawStringShadow(g, "Enemies", xIdntHead, yHead + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, headerText, fontHeader); hCnt++;
		g.setColor(bodyFill);
		g.fillRect(x2, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, bodyFillHeight * 4 + bodyFillAdd * 4);
		iCnt++;
			Text.drawString(g, "Enemies Seen: " + enemiesSeen, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Fought: " + enemiesFought, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Avoided: " + enemiesAvoided, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Killed: " + enemiesKilled, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
		sCnt++;

	//L2:SHOTS
		iCnt--;
		g.setColor(headerFill);
		g.fillRect(x, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, headHeight);
			Text.drawStringShadow(g, "Enemies", xIdntHead, yHead + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, headerText, fontHeader); hCnt++;
		g.setColor(bodyFill);
		g.fillRect(x2, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, bodyFillHeight * 4 + bodyFillAdd * 4);
			Text.drawString(g, "Enemies Seen: " + enemiesSeen, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Fought: " + enemiesFought, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Avoided: " + enemiesAvoided, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Killed: " + enemiesKilled, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
		sCnt++;

		g.setColor(headerFill);
		g.fillRect(x, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, headHeight);
			Text.drawStringShadow(g, "Enemies", xIdntHead, yHead + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, headerText, fontHeader); hCnt++;
		g.setColor(bodyFill);
		g.fillRect(x2, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, bodyFillHeight * 4 + bodyFillAdd * 4);
		iCnt--;
			Text.drawString(g, "Enemies Seen: " + enemiesSeen, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Fought: " + enemiesFought, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Avoided: " + enemiesAvoided, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
			Text.drawString(g, "Enemies Killed: " + enemiesKilled, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
		sCnt++;

//	//L2:SHOTS
//		iCnt--;
//		g.setColor(headerFill);
//		g.fillRect(x, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, headHeight);
//			Text.drawStringShadow(g, "Shots", xIdntHead, yHead + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, headerText, fontHeader); hCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x2, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, bodyFillHeight * 2 + bodyFillAdd * 2);
//			Text.drawString(g, "Shots Fired (Player): " + bulletsFiredPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Shots Fired (Enemies): " + bulletsFiredEnemies, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//		sCnt++;

	//L3:ACTION COUNT
//		g.setColor(headerFill);
//		g.fillRect(x, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, headHeight);
//			Text.drawStringShadow(g, "Action Count", xIdntHead, yHead + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, headerText, fontHeader); hCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x2, y + (headHeight * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), width, bodyFillHeight * 11 + bodyFillAdd * 11);
//		iCnt--;
//			Text.drawString(g, "Jumped: " + jumpCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Ran: " + runCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Reloaded: " + reloadCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Faced (Up): " + faceTopCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Faced (Down): " + faceBottomCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Faced (Left): " + faceLeftCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Faced (Right): " + faceRightCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Paused: " + pauseCount, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Check Inventory: " + checkInventoryCount, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Start Game: " + startGameCount, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Continue: " + continueGameCount, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//		sCnt++;

//	//L4:ACTION COUNT
//		g.setColor(headerFill);
//		g.fillRect(x + t, y + 300*2, 250, fontHeader.getSize());
//			Text.drawStringShadow(g, "Travel Distance", x + t, y + (fontHeader.getSize() + t) * iCnt, false, headerText, fontHeader); iCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x + t, y + 300*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
//			Text.drawString(g, "Walked: " + playerWalkDistance, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Ran: " + playerRunDistance, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Jumped (x): " + jumpDistanceX, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Jumped (y): " + jumpDistanceY, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Fell (x): " + fallDistanceX, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Fell (y): " + fallDistanceY, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Average Speed: " + avgSpeed, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//	
////RIGHT SIDE (R)
//		x = 400; //Padding/intent from edge of screen
//		iCnt = 1; //Each new line multiplier
//		
//	//R1:DAMAGE
//		g.setColor(headerFill);
//		g.fillRect(x + t, y, 250, fontHeader.getSize());
//			Text.drawStringShadow(g, "Damage", x + t, y + (fontHeader.getSize() + t) * iCnt, false, headerText, fontHeader); iCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x + t, y + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
//			Text.drawString(g, "Times Hit (Player): " + hitCountPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Times Hit (Enemies): " + hitCountEnemies, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Health Lost (Player): " + healthLostPlayer, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Health Lost (Enemies): " + healthLostEnemies, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Death Count: " + deathCount, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//
//	//R2:TIME SPENT
//		g.setColor(headerFill);
//		g.fillRect(x + t, y + 100*2, 250, fontHeader.getSize());
//			Text.drawStringShadow(g, "Time Spent", x + t, y + (fontHeader.getSize() + t) * iCnt, false, headerText, fontHeader); iCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x + t, y + 100*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
//			Text.drawString(g, "Grounded: " + timeGrounded, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Airborne: " + timeAirbourne, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Walking: " + timeWalking, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Running: " + timeRunning, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Facing (Up): " + timeFacingUp, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Facing (Down): " + timeFacingDown, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Facing (Left): " + timeFacingLeft, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Facing (Right): " + timeFacingRight, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//		
//	//R3:TIME PLAYED
//		g.setColor(headerFill);
//		g.fillRect(x + t, y + 200*2, 250, fontHeader.getSize());
//			Text.drawStringShadow(g, "Time Played", x + t, y + (fontHeader.getSize() + t) * iCnt, false, headerText, fontHeader); iCnt++;
//		g.setColor(bodyFill);
//		g.fillRect(x + t, y + 200*2 + (fontBody.getSize() + t * j) + 10, 250, fontHeader.getSize() * 2);
//			Text.drawString(g, "Total Time: " + totalTimePlayed, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
//			Text.drawString(g, "Game: " + gameTimePlayed, xIdntBody, (yBody * hCnt) + (bodyHeight * iCnt) + (sHeight * sCnt), false, bodyText, fontBody); iCnt++;
	
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
