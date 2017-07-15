package de.bitbrain.mindmazer.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import aurelienribon.tweenengine.Tween;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.tweens.GameObjectTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer.LevelStageRenderListener;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;
import de.bitbrain.mindmazer.ui.Toast;

public class PreviewManager {

   private final LevelManager levelManager;
   private final GameObject player;
   private final GameObject level;
   private final GameCamera camera;
   private boolean previewed;

   public PreviewManager(LevelManager levelManager, GameObject player, GameObject level, GameCamera camera) {
      this.levelManager = levelManager;
      this.player = player;
      this.level = level;
      this.camera = camera;
   }

   public boolean isPreviewed() {
      return previewed;
   }

   public void preview() {
      previewed = true;
      player.getScale().set(0f, 0f);
      player.setActive(false);
      camera.setTarget(level, false);
      camera.setBaseZoom(calculateBaseZoom());
      levelManager.revealLevel();
      Toast.getInstance().makeToast(Bundle.translations.get(Messages.TOAST_PREPARE));
   }

   public void initialPreview() {
      previewed = true;
      player.getScale().set(0f, 0f);
      player.setActive(false);
      camera.setTarget(level, false);
      camera.setBaseZoom(calculateBaseZoom());
      levelManager.revealLevel(false);
      Toast.getInstance().makeToast(Bundle.translations.get(Messages.TOAST_PREPARE));
   }

   public void obscure() {
      SharedAssetManager.getInstance().get(Assets.Sounds.OBSCURE_MAP, Sound.class).play(0.4f, 1f, 0f);
      previewed = false;
      // Disable player zoom for now, it's easier for the gameplay
      // camera.setTarget(player, false);
      // camera.setBaseZoom(Config.BASE_ZOOM);
      Tween.to(player, GameObjectTween.SCALE, 0.4f).target(1f).start(SharedTweenManager.getInstance());
      levelManager.obscureLevel(new LevelStageRenderListener() {
         @Override
         public void afterSetStage() {
            player.setActive(true);
         }
      });
      camera.setTarget(player, false);
   }

   private float calculateBaseZoom() {
      float screenWidth = Gdx.graphics.getWidth() * Config.BASE_ZOOM;
      float screenHeight = Gdx.graphics.getHeight() * Config.BASE_ZOOM;
      float levelWidth = levelManager.getCurrentStage().getLevelWidth();
      float levelHeight = levelManager.getCurrentStage().getLevelHeight();
      if (levelHeight > screenHeight) {
         return Config.BASE_ZOOM + Config.BASE_ZOOM * (levelHeight / screenHeight);
      }
      if (levelWidth > screenWidth) {
         return Config.BASE_ZOOM + Config.BASE_ZOOM * (levelWidth / screenWidth);
      }
      return Config.BASE_ZOOM;
   }
}
