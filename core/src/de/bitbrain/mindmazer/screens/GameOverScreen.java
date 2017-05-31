package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.postprocessing.filters.Blur.BlurType;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.tweens.ActorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.ui.Styles;

public class GameOverScreen extends AbstractScreen<MindmazerGame> {

   private final GameStats stats;

   private boolean fadingOut = false;

   public GameOverScreen(MindmazerGame game, GameStats stats) {
      super(game);
      this.stats = stats;
   }

   @Override
   public void dispose() {
      super.dispose();
      getAudioManager().stopMusic(Assets.Musics.GAMEOVER);
   }

   @Override
   protected void onCreateStage(Stage stage, int width, int height) {
      setBackgroundColor(Colors.BACKGROUND);
      getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      getAudioManager().fadeInMusic(Assets.Musics.GAMEOVER);
      setupUI(stage);
      setupShaders();
      getScreenTransitions().in(1f);
   }
   
   @Override
   protected void onUpdate(float delta) {
      super.onUpdate(delta);
      if (!fadingOut && Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
         Gdx.input.setInputProcessor(null);
         fadingOut = true;
         getScreenTransitions().out(new MenuScreen(getGame()), 1f);
      }
   }
   
   private void setupUI(Stage stage) {
	   
	   final float animA = 1f;
	   final float animB = 0.5f;
	   final float animC = 0.5f;
	   final float animD = 0.5f;
	   final float animTotal = animA + animB + animC + animD;
	   Table layout = new Table();
	   layout.setFillParent(true);
	   stage.addActor(layout);
	   Label gameOver = new Label("GAME OVER!", Styles.LABEL_CAPTION);
	   layout.add(gameOver).height(50f).padBottom(60f).row();
	   Label points = new Label(stats.getPoints() + " points", Styles.LABEL_TEXT_INFO);
	   layout.add(points).height(120f).row();
	   Label totalSteps = new Label(stats.getTotalSteps() + " total steps", Styles.LABEL_TEXT_INFO);
	   layout.add(totalSteps).height(120f).row();
	   Label continueText = new Label("Press anything to continue...", Styles.LABEL_TEXT_CREDITS);
	   layout.add(continueText).height(20f).padTop(100f).row();
	   
	   // ANIM1 - LOGO
	   gameOver.getColor().a = 0f;
	   gameOver.setScale(0.3f);
	   Tween.to(gameOver, ActorTween.SCALE, animA)
            .target(1f)
            .ease(TweenEquations.easeInBounce)
            .start(SharedTweenManager.getInstance());
	   Tween.to(gameOver, ActorTween.ALPHA, animA * 2f)
       		.target(1f)
       		.start(SharedTweenManager.getInstance());	
	   
	   // ANIM2 - STATISTICS 1
	   points.getColor().a = 0f;
	   points.setScale(0.3f);
	   Tween.to(points, ActorTween.SCALE, animB)
	   	    .delay(animA)
            .target(1f)
            .ease(TweenEquations.easeInCubic)
            .start(SharedTweenManager.getInstance());
	   Tween.to(points, ActorTween.ALPHA, animB * 2f)
	        .delay(animA)
       		.target(1f)
       		.start(SharedTweenManager.getInstance());	
	   
	   // ANIM3 - STATISTICS 2
	   totalSteps.getColor().a = 0f;
	   totalSteps.setScale(0.3f);
	   Tween.to(totalSteps, ActorTween.SCALE, animC)
	   	    .delay(animA + animB)
            .target(1f)
            .ease(TweenEquations.easeInCubic)
            .start(SharedTweenManager.getInstance());
	   Tween.to(totalSteps, ActorTween.ALPHA, animC * 2f)
	        .delay(animA + animB)
       		.target(1f)
       		.start(SharedTweenManager.getInstance());	
	   
	   // ANIM4 - CONTINUE
	   continueText.setScale(0f);
	   continueText.getColor().a = 0f;
	   Tween.to(continueText, ActorTween.SCALE, animD)
            .delay(animA + animB + animC)
            .target(1f)
            .start(SharedTweenManager.getInstance());
	   Tween.to(continueText, ActorTween.ALPHA, animD)
	        .delay(animA + animB + animC)
	        .target(1f)
	        .start(SharedTweenManager.getInstance());	   
	   
	   Tween.to(continueText, ActorTween.SCALE, 1f)
	        .delay(animTotal)
	        .target(0.7f)
	        .repeatYoyo(Tween.INFINITY, 0f)
	        .start(SharedTweenManager.getInstance());
	   Tween.to(continueText, ActorTween.ALPHA, 1f)
	   .delay(animTotal)
       .target(0.4f)
       .repeatYoyo(Tween.INFINITY, 0f)
       .start(SharedTweenManager.getInstance());
   }

   private void setupShaders() {
      RenderPipe worldPipe = getRenderPipeline().getPipe(RenderPipeIds.WORLD);
      Bloom bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.9f), Math.round(Gdx.graphics.getHeight() * 0.9f));

      bloom.setBaseIntesity(0.8f);
      bloom.setBaseSaturation(1.7f);
      bloom.setBlurType(BlurType.Gaussian5x5b);
      bloom.setBlurAmount(0.8f);
      bloom.setBloomSaturation(0.8f);
      bloom.setBloomIntesity(0.9f);
      bloom.setBlurPasses(7);
      Vignette vignette = new Vignette(Math.round(Gdx.graphics.getWidth() / 2f),
            Math.round(Gdx.graphics.getHeight() / 2f), false);
      vignette.setIntensity(0.45f);
      worldPipe.addEffects(vignette);
      RenderPipe uiPipe = getRenderPipeline().getPipe(RenderPipeIds.UI);
      uiPipe.addEffects(bloom);
   }

}
