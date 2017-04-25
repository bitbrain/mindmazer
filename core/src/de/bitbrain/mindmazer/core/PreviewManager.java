package de.bitbrain.mindmazer.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer.LevelStageRenderListener;

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
      SharedAssetManager.getInstance().get(Assets.Sounds.SHOW_MAP, Sound.class).play(0.4f, 1f, 0f);
      previewed = true;
      player.setActive(false);
      camera.setTarget(level, false);
      camera.setBaseZoom(calculateBaseZoom());
      levelManager.revealLevel();
   }

   public void initialPreview() {
      SharedAssetManager.getInstance().get(Assets.Sounds.SHOW_MAP, Sound.class).play(0.4f, 1f, 0f);
      previewed = true;
      player.setActive(false);
      camera.setTarget(level, false);
      camera.setBaseZoom(calculateBaseZoom());
      levelManager.revealLevel(false);
   }

   public void obscure() {
      SharedAssetManager.getInstance().get(Assets.Sounds.OBSCURE_MAP, Sound.class).play(0.4f, 1f, 0f);
      previewed = false;
      camera.setTarget(player, false);
      camera.setBaseZoom(Config.BASE_ZOOM);
      levelManager.obscureLevel(new LevelStageRenderListener() {
         @Override
         public void afterSetStage() {
            player.setActive(true);
         }
      });
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
