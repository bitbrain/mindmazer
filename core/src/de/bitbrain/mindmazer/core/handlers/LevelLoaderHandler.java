package de.bitbrain.mindmazer.core.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.screens.ScreenTransitions;
import de.bitbrain.braingdx.screens.TransitionCallback;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;
import de.bitbrain.mindmazer.levelgen.LevelStage;
import de.bitbrain.mindmazer.preferences.PrefKeys;
import de.bitbrain.mindmazer.ui.Toast;
import de.bitbrain.mindmazer.util.StringUtils;

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
         Toast.getInstance().makeToast(Bundle.translations.get(Messages.TOAST_COMPLETE), Colors.CELL_A, 2f, 1f);
         ScreenTransitions.getInstance().out(new TransitionCallback() {
            @Override
            public void afterTransition() {
               ScreenTransitions.getInstance().in(1f);
               LevelStage stage = levelManager.generateLevelStage(StringUtils.generateRandomString(Config.SEED_STRING_LENGTH));
               Preferences prefs = Gdx.app.getPreferences(Config.PREFERENCE_ID);
               stats.reset();
               stats.addPoint();
               stats.nextStage();
               player.setPosition(stage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
                     stage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
               prefs.putString(PrefKeys.LEVEL_SEED, stage.getSeedAsString());
               prefs.flush();
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
