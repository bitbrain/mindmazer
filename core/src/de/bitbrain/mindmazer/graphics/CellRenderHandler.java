package de.bitbrain.mindmazer.graphics;

import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class CellRenderHandler implements RasteredMovementListener {

   private final LevelManager levelManager;

   public CellRenderHandler(LevelManager levelManager) {
      this.levelManager = levelManager;
   }

   @Override
   public void moveAfter(GameObject object) {
      LevelStage stage = levelManager.getCurrentStage();
      if (stage != null) {
         int indexX = levelManager.getCurrentStage().convertToIndexX(object.getLeft());
         int indexY = levelManager.getCurrentStage().convertToIndexY(object.getTop());
         if (stage.getCompleteCell(indexX, indexY) > 0) {
            levelManager.setCurrentData(indexX, indexY, (byte) 1);
         }
      }
   }

   @Override
   public void moveBefore(GameObject arg0, float arg1, float arg2, float arg3) {
      // noOp
   }

}
