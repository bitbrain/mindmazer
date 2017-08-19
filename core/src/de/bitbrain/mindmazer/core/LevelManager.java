package de.bitbrain.mindmazer.core;

import com.badlogic.gdx.Gdx;

import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Types;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer.LevelStageRenderListener;
import de.bitbrain.mindmazer.levelgen.LevelGenerator;
import de.bitbrain.mindmazer.levelgen.LevelStage;
import de.bitbrain.mindmazer.util.LogTags;

public class LevelManager {

   private final GameObjectRenderManager renderManager;
   private final GameObject world;
   private final LevelGenerator generator = new LevelGenerator();

   private LevelStage currentStage;
   private LevelStageRenderer currentRenderer;

   public LevelManager(GameObjectRenderManager renderManager, GameObject world) {
      this.renderManager = renderManager;
      this.world = world;
   }

   public LevelStage getCurrentStage() {
      return currentStage;
   }

   public void obscureLevel(LevelStageRenderListener callback) {
      Gdx.app.debug(LogTags.LEVELGEN, "Obscuring level...");
      currentRenderer.setStage(currentStage.getCurrentData(), true, callback);
   }

   public void revealLevel() {
      Gdx.app.debug(LogTags.LEVELGEN, "Revealing level...");
      currentRenderer.setStage(currentStage.getCompleteData(), true);
   }

   public void revealLevel(boolean override) {
      Gdx.app.debug(LogTags.LEVELGEN, "Revealing level (override)...");
      currentRenderer.setStage(currentStage.getCompleteData(), override);
   }

   public void setCurrentData(int indexX, int indexY, byte value) {
      if (!currentStage.containsAlready(indexX, indexY, value)) {
         currentStage.setCurrentData(indexX, indexY, value);
         currentRenderer.addCell(indexX, indexY);
      }
   }

   public void resetCurrentStage() {
      Gdx.app.debug(LogTags.LEVELGEN, "Resetting current stage...");
      currentStage.resetCurrentData();
      currentStage.setCurrentData(currentStage.getAbsoluteStartOffsetX(0), currentStage.getAbsoluteStartOffsetY(0),
            (byte) 1);
      currentRenderer.reset();
   }

   public LevelStage generateLevelStage(String seed) {
      Gdx.app.debug(LogTags.LEVELGEN, "Generating new level...");
      currentStage = generator.generateLevel(seed);
      // Enable the first cell by default
      currentStage.setCurrentData(currentStage.getAbsoluteStartOffsetX(0), currentStage.getAbsoluteStartOffsetY(0),
            (byte) 1);
      if (currentRenderer == null) {
         Gdx.app.debug(LogTags.INIT, "Creating new LevelStageRenderer...");
         currentRenderer = new LevelStageRenderer(currentStage.getCurrentData());
         renderManager.register(Types.WORLD, currentRenderer);
      } else {
         Gdx.app.debug(LogTags.LEVELGEN, "Setting current stage on existing LevelStageRenderer!");
         currentRenderer.setStage(currentStage.getCurrentData(), false);
      }
      world.setDimensions(currentStage.getLevelWidth(), currentStage.getLevelHeight());
      Gdx.app.debug(LogTags.LEVELGEN, "Generated new level: length=" + currentStage.getLength());
      return currentStage;
   }
}
