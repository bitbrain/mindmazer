package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.postprocessing.filters.Blur.BlurType;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.core.GameStats;

public class GameOverScreen extends AbstractScreen<MindmazerGame> {

   private final GameStats stats;

   private boolean fadingOut = false;

   public GameOverScreen(MindmazerGame game, GameStats stats) {
      super(game);
      this.stats = stats;
   }

   @Override
   protected void onCreateStage(Stage stage, int width, int height) {
      setBackgroundColor(Colors.BACKGROUND);
      getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
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
