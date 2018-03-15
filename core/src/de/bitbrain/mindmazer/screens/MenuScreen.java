package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.postprocessing.filters.Blur.BlurType;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.screens.TransitionCallback;
import de.bitbrain.braingdx.tweens.ActorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;
import de.bitbrain.mindmazer.preferences.PrefKeys;
import de.bitbrain.mindmazer.tweens.PointLightTween;
import de.bitbrain.mindmazer.ui.HeightedLabel;
import de.bitbrain.mindmazer.ui.Styles;

public class MenuScreen extends AbstractScreen<MindmazerGame> {

   static {
      Tween.registerAccessor(PointLight.class, new PointLightTween());
      Tween.registerAccessor(Label.class, new ActorTween());
   }
   
   private GameContext context;

   public MenuScreen(MindmazerGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext context) {
	  this.context = context;
      setBackgroundColor(Colors.BACKGROUND);
      context.getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      setupUI(context.getStage());
      setupShaders();
      setupFX();
      context.getScreenTransitions().in(1f);
      context.getAudioManager().playMusic(Assets.Musics.MAINMENU);
   }

   @Override
   public void dispose() {
      SharedAssetManager.getInstance().get(Assets.Musics.MAINMENU, Music.class).stop();
   }

   private void setupFX() {
      TweenManager tweenManager = SharedTweenManager.getInstance();
      PointLight lightA = context.getLightingManager().addPointLight("backgroundLightA",
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
      PointLight lightB = context.getLightingManager().addPointLight("backgroundLightB",
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
      Preferences prefs = Gdx.app.getPreferences(Config.PREFERENCE_ID);
      Table layout = new Table();
      layout.setFillParent(true);
      // Logo
      Label logo = new HeightedLabel(Config.GAME_NAME, Styles.LABEL_TEXT_LOGO);
      layout.add(logo).padBottom(Gdx.graphics.getHeight() / 15f).row();
      
      // Buttons
      String playButtonMsg = Messages.MENU_PLAY;
      if (prefs.contains(PrefKeys.PLAYER_STEPS)) {
          playButtonMsg = Messages.MENU_CONTINUE;
      }
      TextButton play = new TextButton(Bundle.translations.get(playButtonMsg), Styles.TEXTBUTTON_MENU);
      play.addCaptureListener(new ClickListener() {
         @Override
         public void clicked(InputEvent event, float x, float y) {
            Gdx.input.setInputProcessor(null);
            SharedAssetManager.getInstance().get(Assets.Sounds.BUTTON_CLICK, Sound.class).play();
            context.getAudioManager().fadeOutMusic(Assets.Musics.MAINMENU, 1.5f);
            context.getScreenTransitions().out(new IngameScreen(getGame()), 1.5f);
         }
      });
      layout.add(play)
            .width(Gdx.graphics.getWidth() / 1.7f).height(Gdx.graphics.getWidth() / 4.5f)
            .row();
      TextButton close = new TextButton(Bundle.translations.get(Messages.MENU_CLOSE), Styles.TEXTBUTTON_MENU);
      close.addCaptureListener(new ClickListener() {
         @Override
         public void clicked(InputEvent event, float x, float y) {
            Gdx.input.setInputProcessor(null);
            context.getAudioManager().fadeOutMusic(Assets.Musics.MAINMENU, 1.5f);
            SharedAssetManager.getInstance().get(Assets.Sounds.BUTTON_CLICK, Sound.class).play();
            context.getScreenTransitions().out(new TransitionCallback() {
               @Override
               public void afterTransition() {
                  Gdx.app.exit();
               }
               @Override
               public void beforeTransition() {
                  
               }               
            }, 1.5f);
         }
      });
      layout.add(close)
            .width(Gdx.graphics.getWidth() / 1.7f).height(Gdx.graphics.getWidth() / 4.5f)
            .padTop(35f)
            .row();
      // Credits
      String text = Bundle.translations.get(Messages.GENERAL_CREDITS_DEV) + "\n" +
                     Bundle.translations.get(Messages.GENERAL_CREDITS_AUDIO) + "\n" +
                     Config.GAME_VERSION;
      Label credits = new Label(text, Styles.LABEL_TEXT_CREDITS);
      credits.setAlignment(Align.center);
      layout.add(credits).width(Gdx.graphics.getWidth()).height(200f).padTop(60f);
      Tween.to(credits, ActorTween.SCALE, 1f)
           .target(1.1f)
           .repeatYoyo(Tween.INFINITY, 0f)
           .ease(TweenEquations.easeOutCubic)
           .start(context.getTweenManager());
      stage.addActor(layout);
   }

   private void setupShaders() {
   	if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
	      RenderPipe worldPipe = context.getRenderPipeline().getPipe(RenderPipeIds.WORLD);
	     
	      Vignette vignette = new Vignette(Math.round(Gdx.graphics.getWidth() / 2f),
	            Math.round(Gdx.graphics.getHeight() / 2f), false);
	      vignette.setIntensity(0.45f);
	      worldPipe.addEffects(vignette);
	      
	      if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
		      RenderPipe uiPipe = context.getRenderPipeline().getPipe(RenderPipeIds.UI);
		      Bloom bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.9f), Math.round(Gdx.graphics.getHeight() * 0.9f));
		      bloom.setBaseIntesity(0.8f);
		      bloom.setBaseSaturation(1.7f);
		      bloom.setBlurType(BlurType.Gaussian5x5b);
		      bloom.setBlurAmount(0.8f);
		      bloom.setBloomSaturation(0.8f);
		      bloom.setBloomIntesity(0.9f);
		      bloom.setBlurPasses(7);
		      uiPipe.addEffects(bloom);
	      }
   	}
   }

}
