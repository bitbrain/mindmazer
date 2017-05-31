package de.bitbrain.mindmazer.core.handlers;

import com.badlogic.gdx.audio.Sound;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.screens.ScreenTransitions;
import de.bitbrain.braingdx.screens.TransitionCallback;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class LevelLoaderHandler implements RasteredMovementListener {

   private final LevelManager levelManager;
   private final GameObject player;
   private final GameStats stats;
   private final PreviewManager previewManager;

   public LevelLoaderHandler(LevelManager levelManager, GameObject player, GameStats stats,
         PreviewManager previewManager) {
      this.levelManager = levelManager;
      this.player = player;
      this.stats = stats;
      this.previewManager = previewManager;
   }

   @Override
   public void moveAfter(final GameObject object) {
      if (hasEnteredLastAvailableCell(object)) {
         SharedAssetManager.getInstance().get(Assets.Sounds.LEVEL_COMPLETE, Sound.class).play();
         player.setActive(false);
         ScreenTransitions.getInstance().out(new TransitionCallback() {
            @Override
            public void afterTransition() {
               ScreenTransitions.getInstance().in(1f);
               LevelStage stage = levelManager.generateLevelStage();
               stats.reset();
               stats.addPoint();
               player.setPosition(stage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
                     stage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
               previewManager.initialPreview();
            }

            @Override
            public void beforeTransition() {

            }
         }, 1.0f);
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
