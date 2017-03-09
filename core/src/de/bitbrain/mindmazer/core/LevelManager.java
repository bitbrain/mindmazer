package de.bitbrain.mindmazer.core;

import de.bitbrain.braingdx.graphics.GameObjectRenderManager;
import de.bitbrain.mindmazer.Types;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer;
import de.bitbrain.mindmazer.levelgen.LevelGenerator;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class LevelManager {

   private final GameObjectRenderManager renderManager;
   private final LevelGenerator generator = new LevelGenerator();

   private LevelStage currentStage;
   private LevelStageRenderer currentRenderer;

   public LevelManager(GameObjectRenderManager renderManager) {
      this.renderManager = renderManager;
   }

   public LevelStage getCurrentStage() {
      return currentStage;
   }

   public void obscureLevel() {
      currentRenderer.setStage(currentStage.getCurrentData());
   }

   public void revealLevel() {
      currentRenderer.setStage(currentStage.getCompleteData());
   }

   public void setCurrentData(int indexX, int indexY, byte value) {
      currentStage.setCurrentData(indexX, indexY, value);
      currentRenderer.addCell(indexX, indexY);
   }

   public void resetCurrentStage() {
      currentStage.resetCurrentData();
      currentRenderer.setStage(currentStage.getCurrentData());
      currentStage.setCurrentData(currentStage.getAbsoluteStartOffsetX(0), currentStage.getAbsoluteStartOffsetY(0),
            (byte) 1);
      currentRenderer.reset();
   }

   public LevelStage generateLevelStage() {
      currentStage = generator.generateLevel(6);
      // Enable the first cell by default
      currentStage.setCurrentData(currentStage.getAbsoluteStartOffsetX(0), currentStage.getAbsoluteStartOffsetY(0),
            (byte) 1);
      if (currentRenderer == null) {
         currentRenderer = new LevelStageRenderer(currentStage.getCurrentData());
         renderManager.register(Types.WORLD, currentRenderer);
      } else {
         currentRenderer.setStage(currentStage.getCurrentData());
      }
      return currentStage;
   }
}
