package de.bitbrain.mindmazer.core.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.graphics.GameCamera;
import de.bitbrain.braingdx.screens.ScreenTransitions;
import de.bitbrain.braingdx.tweens.GameObjectTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;
import de.bitbrain.mindmazer.screens.GameOverScreen;
import de.bitbrain.mindmazer.ui.Toast;
import de.bitbrain.mindmazer.util.LogTags;

public class GameOverHandler implements RasteredMovementListener {

   private final LevelManager levelManager;
   private final GameCamera camera;
   private final TweenManager tweenManager = SharedTweenManager.getInstance();
   private final PreviewManager previewManager;
   private final MindmazerGame game;
   private final GameStats stats;

   public GameOverHandler(LevelManager levelManager, GameCamera camera,
         PreviewManager previewManager, MindmazerGame game, GameStats stats) {
      this.levelManager = levelManager;
      this.camera = camera;
      this.previewManager = previewManager;
      this.game = game;
      this.stats = stats;
   }

   @Override
   public void moveAfter(GameObject object) {
      boolean isOutOfLevel = isObjectOutOfLevel(object);
      if (isOutOfLevel) {
         SharedAssetManager.getInstance().get(Assets.Sounds.DEATH, Sound.class).play();
         stats.reduceLife();
         Toast.getInstance().makeToast(Bundle.translations.get(Messages.TOAST_DEATH), Color.valueOf("#ff0060"), 2f, 1.5f);
      }
      if (isGameOver()) {
         SharedAssetManager.getInstance().get(Assets.Sounds.GAME_OVER, Sound.class).play();
         Gdx.app.getPreferences(Config.PREFERENCE_ID).clear();
         Gdx.app.getPreferences(Config.PREFERENCE_ID).flush();
         stats.reset();
         object.setActive(false);
         ScreenTransitions.getInstance().out(new GameOverScreen(game, stats), 1.5f);
      } else if (isOutOfLevel) {
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
      ScreenTransitions.getInstance().out(1f);
      Tween.to(object, GameObjectTween.ALPHA, 1.0f).target(0f).ease(TweenEquations.easeOutQuad).start(tweenManager);
      Tween.to(object, GameObjectTween.SCALE, 1.0f).target(0.2f).ease(TweenEquations.easeOutQuad)
      .setCallbackTriggers(TweenCallback.COMPLETE)
      .setCallback(new TweenCallback() {
         @Override
         public void onEvent(int arg0, BaseTween<?> arg1) {
                  Gdx.app.log(LogTags.GAMEPLAY, "Respawning...");
                  ScreenTransitions.getInstance().in(1.5f);
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
