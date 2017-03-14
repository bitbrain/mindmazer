package de.bitbrain.mindmazer.core;

import de.bitbrain.braingdx.util.DeltaTimer;

public class GameStats {

   private int totalSteps;
   private int currentSteps;
   private DeltaTimer timer = new DeltaTimer();
   private final LevelManager levelManager;

   public GameStats(LevelManager levelManager) {
      this.levelManager = levelManager;
   }

   public void update(float delta) {
      timer.update(delta);
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
