package de.bitbrain.mindmazer.ui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import de.bitbrain.braingdx.tweens.ColorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.ValueTween;
import de.bitbrain.braingdx.util.ValueProvider;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.core.GameStats;

public class GameProgressLabel extends HeightedLabel {

   private static final float INTERVAL = 0.4f;

   private final GameStats stats;

   private int lastPercentage;

   private ValueProvider valueProvider = new ValueProvider();

   static {
      Tween.registerAccessor(ValueProvider.class, new ValueTween());
   }

   public GameProgressLabel(GameStats stats) {
      super("0%", Colors.CELL_B_DARK, Styles.LABEL_TEXT_INFO);
      setColor(Colors.CELL_A_DARK.cpy());
      this.stats = stats;
   }

   @Override
   public void act(float delta) {
      int currentPercentage = getCurrentPercentage();
      if (currentPercentage != lastPercentage) {
         setColor(Colors.CELL_A.cpy());
         SharedTweenManager.getInstance().killTarget(getColor());
         SharedTweenManager.getInstance().killTarget(valueProvider);
         Tween.to(valueProvider, ValueTween.VALUE, INTERVAL)
            .target(currentPercentage)
            .ease(TweenEquations.easeNone)
            .start(SharedTweenManager.getInstance());
         Tween.to(getColor(), ColorTween.R, INTERVAL * 3)
               .target(Colors.CELL_A_DARK.r)
            .ease(TweenEquations.easeInCubic)
            .start(SharedTweenManager.getInstance());
         Tween.to(getColor(), ColorTween.G, INTERVAL * 3)
               .target(Colors.CELL_A_DARK.g)
            .ease(TweenEquations.easeInCubic)
            .start(SharedTweenManager.getInstance());
         Tween.to(getColor(), ColorTween.B, INTERVAL * 3)
               .target(Colors.CELL_A_DARK.b)
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
