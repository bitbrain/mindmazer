package de.bitbrain.mindmazer.core;

import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.mindmazer.Config;

public class GameStats {

   private int totalSteps;
   private int currentSteps;
   private int life = Config.DEFAULT_LIFE;
   private DeltaTimer timer = new DeltaTimer();
   private final LevelManager levelManager;

   public GameStats(LevelManager levelManager) {
      this.levelManager = levelManager;
   }

   public void update(float delta) {
      timer.update(delta);
   }

   public int getLife() {
      return life;
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

   public void step() {
      totalSteps++;
      currentSteps++;
   }

   public int getLevelSteps() {
      return levelManager.getCurrentStage().getLength();
   }

}
