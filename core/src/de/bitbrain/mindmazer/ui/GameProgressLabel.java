package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import de.bitbrain.braingdx.tweens.ColorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.ValueTween;
import de.bitbrain.braingdx.util.ValueProvider;
import de.bitbrain.mindmazer.core.GameStats;

public class GameProgressLabel extends Label {

   private static final float INTERVAL = 0.4f;
   private static final float ALPHA = 0.6f;

   private final GameStats stats;

   private int lastPercentage;

   private ValueProvider valueProvider = new ValueProvider();

   static {
      Tween.registerAccessor(ValueProvider.class, new ValueTween());
   }

   public GameProgressLabel(GameStats stats) {
      super("0%", Styles.LABEL_TEXT_INFO);
      getColor().a = ALPHA;
      this.stats = stats;
   }

   @Override
   public void act(float delta) {
      int currentPercentage = getCurrentPercentage();
      if (currentPercentage != lastPercentage) {
         getColor().a = 1.0f;
         SharedTweenManager.getInstance().killTarget(getColor());
         SharedTweenManager.getInstance().killTarget(valueProvider);
         Tween.to(valueProvider, ValueTween.VALUE, INTERVAL)
              .target(currentPercentage)
              .ease(TweenEquations.easeNone)
              .start(SharedTweenManager.getInstance());
         Tween.to(getColor(), ColorTween.A, INTERVAL * 2)
              .target(ALPHA)
              .delay(INTERVAL)
              .ease(TweenEquations.easeInCubic)
              .start(SharedTweenManager.getInstance());
      }
      setText((int) valueProvider.getValue() + "%");
      lastPercentage = getCurrentPercentage();
      super.act(delta);
   }

   private int getCurrentPercentage() {
      return Math.round((float) stats.getCurrentSteps() / (float) (stats.getLevelSteps() - 1) * 100f);
   }

}
