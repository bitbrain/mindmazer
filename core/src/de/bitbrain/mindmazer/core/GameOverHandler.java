package de.bitbrain.mindmazer.core;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.tweens.GameObjectTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class GameOverHandler implements RasteredMovementListener {

   private final LevelStage stage;
   private final GameCamera camera;
   private final TweenManager tweenManager = SharedTweenManager.getInstance();

   public GameOverHandler(LevelStage stage, GameCamera camera) {
      this.stage = stage;
      this.camera = camera;
   }

   @Override
   public void moveAfter(GameObject object) {
      int indexX = stage.convertToIndexX(object.getLeft());
      int indexY = stage.convertToIndexY(object.getTop());
      if (stage.getCompleteCell(indexX, indexY) == 0) {
         respawn(object);
      }
   }

   @Override
   public void moveBefore(GameObject object, float moveX, float moveY, float interval) {
      // noOp
   }

   private void respawn(final GameObject object) {
      object.setActive(false);
      camera.setTarget(null);
      tweenManager.killTarget(object);
      Tween.to(object, GameObjectTween.SCALE, 1.0f).target(0f).ease(TweenEquations.easeOutExpo)
      .setCallbackTriggers(TweenCallback.COMPLETE)
      .setCallback(new TweenCallback() {
         @Override
         public void onEvent(int arg0, BaseTween<?> arg1) {
                  object.setPosition(stage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
                        stage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
                  camera.setTarget(object);
                  object.getScale().set(1f, 1f);
                  object.setActive(true);
               }
      }).start(tweenManager);


   }

}
