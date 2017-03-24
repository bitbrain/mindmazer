package de.bitbrain.mindmazer.core.handlers;

import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.graphics.ScreenFader;
import de.bitbrain.mindmazer.graphics.ScreenFader.ScreenFadeCallback;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class LevelLoaderHandler implements RasteredMovementListener {

   private final LevelManager levelManager;
   private final GameObject player;
   private final GameStats stats;
   private final ScreenFader fader;
   private final PreviewManager previewManager;

   public LevelLoaderHandler(LevelManager levelManager, GameObject player, GameStats stats, ScreenFader fader,
         PreviewManager previewManager) {
      this.levelManager = levelManager;
      this.player = player;
      this.stats = stats;
      this.fader = fader;
      this.previewManager = previewManager;
   }

   @Override
   public void moveAfter(final GameObject object) {
      if (hasEnteredLastAvailableCell(object)) {
         player.setActive(false);
         fader.fadeOut(new ScreenFadeCallback() {
            @Override
            public void afterFade() {
               LevelStage stage = levelManager.generateLevelStage();
               stats.reset();
               player.setPosition(stage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
                     stage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
               previewManager.preview();
            }
         }, 0.5f);
      }
   }

   @Override
   public void moveBefore(GameObject arg0, float arg1, float arg2, float arg3) {
      // TODO Auto-generated method stub

   }

   private boolean hasEnteredLastAvailableCell(GameObject object) {
      LevelStage stage = levelManager.getCurrentStage();
      if (stage != null) {
         int indexX = stage.convertToIndexX(object.getLeft());
         int indexY = stage.convertToIndexY(object.getTop());
         return indexX == stage.getLastCellX() && indexY == stage.getLastCellY();
      }
      return false;
   }

}
