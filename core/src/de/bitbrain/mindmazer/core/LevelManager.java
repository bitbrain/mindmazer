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

   public void obscureLevel() {
      currentRenderer.setStage(currentStage.getCurrentData());
   }

   public void revealLevel() {
      currentRenderer.setStage(currentStage.getCompleteData());
   }

   public LevelStage generateLevelStage() {
      currentStage = generator.generateLevel(6);
      currentRenderer = new LevelStageRenderer(currentStage.getCompleteData());
      renderManager.register(Types.WORLD, currentRenderer);
      return currentStage;
   }
}
