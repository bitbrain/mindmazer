package de.bitbrain.mindmazer.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import de.bitbrain.braingdx.util.DeltaTimer;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.preferences.PrefKeys;

public class GameStats {

   private int totalSteps;
   private int currentSteps;
   private int maxStageSteps;
   private int stage;
   private int life;
   private DeltaTimer timer = new DeltaTimer();
   private int points;
   
   public GameStats() {
   	Preferences prefs = prefs();
   	life = prefs.getInteger(PrefKeys.PLAYER_LIFE, Config.DEFAULT_LIFE);
   	stage = prefs.getInteger(PrefKeys.LEVEL_STAGE, 1);
   	points = prefs.getInteger(PrefKeys.PLAYER_POINTS, 0);
   	currentSteps = prefs.getInteger(PrefKeys.LEVEL_CURRENT_STEPS, 0);
   	maxStageSteps = prefs.getInteger(PrefKeys.LEVEL_MAX_STEPS, 0);
   	totalSteps = prefs.getInteger(PrefKeys.PLAYER_STEPS, 0);
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
   	prefs().putInteger(PrefKeys.LEVEL_STAGE, stage);
   	prefs().flush();
   }

   public void addLife() {
      if (life > 0) {
         life++;
         prefs().putInteger(PrefKeys.PLAYER_LIFE, life);
         prefs().flush();
      }
   }

   public void reduceLife() {
      if (life > 0) {
         life--;
         prefs().putInteger(PrefKeys.PLAYER_LIFE, life);
         prefs().flush();
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
      prefs().putInteger(PrefKeys.LEVEL_CURRENT_STEPS, currentSteps);
      prefs().flush();
   }
   
   public void addPoint() {
      maxStageSteps = 0;
	   points++;
	   prefs().putInteger(PrefKeys.PLAYER_POINTS, points);
	   prefs().putInteger(PrefKeys.LEVEL_MAX_STEPS, maxStageSteps);
	   prefs().flush();
   }
   
   public int getPoints() {
	   return points;
   }

   public void step() {
      currentSteps++;
      if (currentSteps > maxStageSteps) {
      	maxStageSteps++;
      	totalSteps++;
      }
      prefs().putInteger(PrefKeys.LEVEL_MAX_STEPS, maxStageSteps);
      prefs().putInteger(PrefKeys.LEVEL_CURRENT_STEPS, currentSteps);
      prefs().putInteger(PrefKeys.PLAYER_STEPS, totalSteps);
      prefs().flush();
   }
   
   private Preferences prefs() {
   	return Gdx.app.getPreferences(Config.PREFERENCE_ID);
   }
}
