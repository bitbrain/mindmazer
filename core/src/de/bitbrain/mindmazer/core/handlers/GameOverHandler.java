package de.bitbrain.mindmazer.core.handlers;

import com.badlogic.gdx.Gdx;

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
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.graphics.ScreenFader;
import de.bitbrain.mindmazer.graphics.ScreenFader.ScreenFadeCallback;
import de.bitbrain.mindmazer.screens.GameOverScreen;

public class GameOverHandler implements RasteredMovementListener {

   private final LevelManager levelManager;
   private final GameCamera camera;
   private final TweenManager tweenManager = SharedTweenManager.getInstance();
   private final ScreenFader fader;
   private final PreviewManager previewManager;
   private final MindmazerGame game;
   private final GameStats stats;

   public GameOverHandler(LevelManager levelManager, GameCamera camera, ScreenFader fader,
         PreviewManager previewManager, MindmazerGame game, GameStats stats) {
      this.levelManager = levelManager;
      this.camera = camera;
      this.fader = fader;
      this.previewManager = previewManager;
      this.game = game;
      this.stats = stats;
   }

   @Override
   public void moveAfter(GameObject object) {
      if (isGameOver()) {
         stats.reset();
         Gdx.input.setInputProcessor(null);
         fader.fadeOut(new ScreenFadeCallback() {
            @Override
            public void afterFade() {
               game.setScreen(new GameOverScreen(game, stats));
            }
         }, 1f);
      } else if (isObjectOutOfLevel(object)) {
         respawn(object);
      }
   }

   @Override
   public void moveBefore(GameObject object, float moveX, float moveY, float interval) {
      // noOp
   }

   private boolean isGameOver() {
      return stats.getLife() < 1;
   }

   private boolean isObjectOutOfLevel(GameObject object) {
      int indexX = levelManager.getCurrentStage().convertToIndexX(object.getLeft());
      int indexY = levelManager.getCurrentStage().convertToIndexY(object.getTop());
      return levelManager.getCurrentStage().getCompleteCell(indexX, indexY) != 1;
   }

   private void respawn(final GameObject object) {
      object.setActive(false);
      camera.setTarget(null);
      tweenManager.killTarget(object);
      fader.fadeOut(null, 1f);
      Tween.to(object, GameObjectTween.ALPHA, 1.0f).target(0f).ease(TweenEquations.easeOutQuad).start(tweenManager);
      Tween.to(object, GameObjectTween.SCALE, 1.0f).target(0.2f).ease(TweenEquations.easeOutQuad)
      .setCallbackTriggers(TweenCallback.COMPLETE)
      .setCallback(new TweenCallback() {
         @Override
         public void onEvent(int arg0, BaseTween<?> arg1) {
                  fader.fadeIn(1f);
                  levelManager.resetCurrentStage();
                  object.setPosition(levelManager.getCurrentStage().getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
                        levelManager.getCurrentStage().getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
                  object.getScale().set(1f, 1f);
                  object.getColor().a = 1f;
                  previewManager.preview();
               }
      }).start(tweenManager);


   }

}
