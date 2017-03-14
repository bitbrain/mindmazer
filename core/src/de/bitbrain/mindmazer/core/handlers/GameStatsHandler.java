package de.bitbrain.mindmazer.core.handlers;

import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class GameStatsHandler implements RasteredMovementListener {

   private final GameStats stats;
   private final LevelManager levelManager;

   public GameStatsHandler(LevelManager levelManager, GameStats stats) {
      this.levelManager = levelManager;
      this.stats = stats;
   }

   @Override
   public void moveAfter(GameObject object) {
      int indexX = levelManager.getCurrentStage().convertToIndexX(object.getLeft());
      int indexY = levelManager.getCurrentStage().convertToIndexY(object.getTop());
      if (levelManager.getCurrentStage().getCompleteCell(indexX, indexY) != 1) {
         stats.reset();
      }
   }

   @Override
   public void moveBefore(GameObject object, float moveX, float moveY, float interval) {
      LevelStage stage = levelManager.getCurrentStage();
      if (stage != null) {
         int indexX = levelManager.getCurrentStage().convertToIndexX(object.getLeft() + moveX);
         int indexY = levelManager.getCurrentStage().convertToIndexY(object.getTop() + moveY);
         if (stage.getCompleteCell(indexX, indexY) > 0 && stage.getCurrentCell(indexX, indexY) == 0) {
            stats.step();
         }
      }
   }

}
