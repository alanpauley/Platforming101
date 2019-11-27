package dev.apauley.utilities;

import dev.apauley.general.Handler;

/*
 * Tracks stats (SO many stats
 */

public class StatTracker {

	private Handler handler;
	
	//Tracks enemies counts
	//Future Idea: convert to map or whatever so can track how many per type in an array/map
	private int enemiesFought = 0
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
	
	private float playerRunDisance = 0
				, playerWalkDistance = 0
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
			    , timeRunning = 0
			    , timeWalking = 0
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

	/*************** GETTERS and SETTERS ***************/	
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getEnemiesFought() {
		return enemiesFought;
	}

	public void setEnemiesFought(int enemiesFought) {
		this.enemiesFought = enemiesFought;
	}

	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
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
		return playerRunDisance;
	}

	public void setPlayerRunDisance(float playerRunDisance) {
		this.playerRunDisance = playerRunDisance;
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
