package de.bitbrain.mindmazer.core;

import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.mindmazer.Config;

public class GameStats {

   private int totalSteps;
   private int currentSteps;
   private int stage = 1;
   private int life = Config.DEFAULT_LIFE;
   private DeltaTimer timer = new DeltaTimer();
   private final LevelManager levelManager;
   private int points;

   public GameStats(LevelManager levelManager) {
      this.levelManager = levelManager;
   }

   public void update(float delta) {
      timer.update(delta);
   }

   public int getLife() {
      return life;
   }
   
   public int getStage() {
   	return stage;
   }
   
   public void nextStage() {
   	stage++;
   }

   public void addLife() {
      if (life > 0) {
         life++;
      }
   }

   public void reduceLife() {
      if (life > 0) {
         life--;
      }
   }

   public float getTime() {
      return timer.getTicks();
   }

   public int getCurrentSteps() {
      return currentSteps;
   }

   public int getTotalSteps() {
      return totalSteps;
   }

   public void reset() {
      currentSteps = 0;
   }
   
   public void addPoint() {
	   points++;
   }
   
   public int getPoints() {
	   return points;
   }

   public void step() {
      currentSteps++;
      if (currentSteps > totalSteps) {
      	totalSteps++;
      }
   }

   public int getLevelSteps() {
      return levelManager.getCurrentStage().getLength();
   }

}
