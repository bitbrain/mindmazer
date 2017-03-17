package de.bitbrain.mindmazer.core;

import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.world.GameObject;

public class PreviewManager {

   private final LevelManager levelManager;
   private final GameObject player;
   private final GameObject level;
   private final GameCamera camera;

   public PreviewManager(LevelManager levelManager, GameObject player, GameObject level, GameCamera camera) {
      this.levelManager = levelManager;
      this.player = player;
      this.level = level;
      this.camera = camera;
   }

   public void preview() {
      player.setActive(false);
      camera.setTarget(level, false);
      levelManager.revealLevel();
   }

   public void obscure() {
      player.setActive(true);
      camera.setTarget(player, false);
      levelManager.obscureLevel();
   }
}
