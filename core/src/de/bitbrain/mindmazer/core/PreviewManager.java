package de.bitbrain.mindmazer.core;

import com.badlogic.gdx.Gdx;

import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;

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
      player.setActive(false);
      camera.setTarget(level, false);
      camera.setBaseZoom(calculateBaseZoom());
      levelManager.revealLevel();
   }

   public void obscure() {
      previewed = false;
      player.setActive(true);
      camera.setTarget(player, false);
      camera.setBaseZoom(Config.BASE_ZOOM);
      levelManager.obscureLevel();
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
