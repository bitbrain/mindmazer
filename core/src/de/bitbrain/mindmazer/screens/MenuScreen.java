package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import box2dLight.PointLight;
import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.postprocessing.filters.Blur.BlurType;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.graphics.ScreenFader;
import de.bitbrain.mindmazer.graphics.ScreenFader.ScreenFadeCallback;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;
import de.bitbrain.mindmazer.tweens.PointLightTween;
import de.bitbrain.mindmazer.ui.HeightedLabel;
import de.bitbrain.mindmazer.ui.Styles;

public class MenuScreen extends AbstractScreen<MindmazerGame> {

   static {
      Tween.registerAccessor(PointLight.class, new PointLightTween());
   }

   private ScreenFader fader;

   public MenuScreen(MindmazerGame game) {
      super(game);
   }

   @Override
   protected void onCreateStage(Stage stage, int width, int height) {
      fader = new ScreenFader();
      getRenderPipeline().set("overlay", fader);
      setBackgroundColor(Colors.BACKGROUND);
      getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      setupUI(stage);
      setupShaders();
      setupFX();
      fader.fadeIn(2.5f);
   }

   private void setupFX() {
      TweenManager tweenManager = SharedTweenManager.getInstance();
      PointLight lightA = getLightingManager().addPointLight("backgroundLightA",
            new Vector2(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 5f), 800f,
            Colors.FOREGROUND);
      Tween.to(lightA, PointLightTween.DISTANCE, 6f)
            .target(500f)
           .ease(TweenEquations.easeInOutQuad)
           .repeatYoyo(Tween.INFINITY, 0)
           .start(tweenManager);
      Tween.to(lightA, PointLightTween.POS_Y, 12f)
            .target(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 5f)
           .repeatYoyo(Tween.INFINITY,  0)
           .start(tweenManager);
      PointLight lightB = getLightingManager().addPointLight("backgroundLightB",
            new Vector2(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 5f), 500f,
            Color.valueOf("76fffa"));
      Tween.to(lightB, PointLightTween.DISTANCE, 6f)
            .target(800f)
           .ease(TweenEquations.easeInOutQuad)
           .repeatYoyo(Tween.INFINITY, 0)
           .start(tweenManager);
      Tween.to(lightB, PointLightTween.POS_Y, 12f)
            .target(Gdx.graphics.getHeight() / 5f)
           .repeatYoyo(Tween.INFINITY,  0)
           .start(tweenManager);
      
      Tween.to(lightA, PointLightTween.POS_X, 4f)
           .target(Gdx.graphics.getWidth() / 3f)
           .repeatYoyo(Tween.INFINITY,  0)
           .start(tweenManager);
      Tween.to(lightB, PointLightTween.POS_X, 4f)
           .target(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3f)
           .repeatYoyo(Tween.INFINITY,  0)
           .start(tweenManager);
   }

   private void setupUI(Stage stage) {
      Table layout = new Table();
      layout.setFillParent(true);
      // Logo
      Label logo = new HeightedLabel(Config.GAME_NAME, Colors.CELL_B_DARK, Styles.LABEL_TEXT_LOGO);
      logo.setColor(Colors.CELL_A);
      layout.add(logo).padBottom(Gdx.graphics.getHeight() / 8f).row();
      // Buttons
      TextButton play = new TextButton(Bundle.translations.get(Messages.MENU_PLAY), Styles.TEXTBUTTON_MENU);
      play.addCaptureListener(new ClickListener() {
         @Override
         public void clicked(InputEvent event, float x, float y) {
            Gdx.input.setInputProcessor(null);
            fader.fadeOut(new ScreenFadeCallback() {
               @Override
               public void afterFade() {
                  getGame().setScreen(new IngameScreen(getGame()));
               }
            }, 0.8f);
         }
      });
      layout.add(play)
            .width(Gdx.graphics.getWidth() / 1.7f).height(Gdx.graphics.getWidth() / 4.5f)
            .row();
      TextButton close = new TextButton(Bundle.translations.get(Messages.MENU_CLOSE), Styles.TEXTBUTTON_MENU);
      layout.add(close)
            .width(Gdx.graphics.getWidth() / 1.7f).height(Gdx.graphics.getWidth() / 4.5f)
            .padTop(25f)
            .row();
      // Credits
      String text = Bundle.translations.get(Messages.GENERAL_CREDITS_DEV) + "\n" +
                     Bundle.translations.get(Messages.GENERAL_CREDITS_AUDIO) + "\n" +
                     "© " + Config.GAME_YEAR;
      Label credits = new Label(text, Styles.LABEL_TEXT_CREDITS);
      credits.setAlignment(Align.center);
      layout.add(credits).width(Gdx.graphics.getWidth()).padTop(60f);
      stage.addActor(layout);
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
