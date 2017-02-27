package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.OrientationMovementController;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.postprocessing.filters.Blur.BlurType;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets.Textures;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer;
import de.bitbrain.mindmazer.graphics.ShadowedRenderer;
import de.bitbrain.mindmazer.levelgen.LevelGenerator;
import de.bitbrain.mindmazer.levelgen.LevelStage;

public class IngameScreen extends AbstractScreen<MindmazerGame> {

   public IngameScreen(MindmazerGame game) {
      super(game);
   }

   @Override
   protected void onCreateStage(Stage stage, int width, int height) {
      setBackgroundColor(Colors.BACKGROUND);
      LevelGenerator levelGenerator = new LevelGenerator();
      LevelStage levelStage = levelGenerator.generateLevel(6);
      GameObject world = getGameWorld().addObject();
      getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      world.setActive(false);
      world.setType("world");
      getRenderManager().register("world", new LevelStageRenderer(levelStage.getCompleteData()));

      GameObject player = getGameWorld().addObject();
      player.setType("player");
      player.setZIndex(10);
      player.setDimensions(Config.TILE_SIZE, Config.TILE_SIZE);
      player.setPosition(levelStage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
            levelStage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
      getRenderManager().register("player", new ShadowedRenderer(new SpriteRenderer(Textures.PLAYER)));
      getGameCamera().setTarget(player);
      getGameCamera().setSpeed(2.2f);
      getGameCamera().setBaseZoom(0.2f);
      getGameCamera().setZoomScale(0.001f);

      OrientationMovementController controller = new OrientationMovementController();
      RasteredMovementBehavior behavior = new RasteredMovementBehavior(controller, levelStage)
            .interval(0.3f).rasterSize(Config.TILE_SIZE, Config.TILE_SIZE);
      getBehaviorManager().apply(behavior, player);
      getBehaviorManager().apply(new PointLightBehavior(Color.WHITE, 200f, getLightingManager()),
            player);
      setupShaders();
   }

   private void setupShaders() {
      RenderPipe worldPipe = getRenderPipeline().getPipe(RenderPipeIds.WORLD);
      Bloom bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.9f), Math.round(Gdx.graphics.getHeight() * 0.9f));

      bloom.setBaseIntesity(0.8f);
      bloom.setBaseSaturation(1.7f);
      bloom.setBlurType(BlurType.Gaussian5x5b);
      bloom.setBlurAmount(0.4f);
      bloom.setBloomSaturation(0.8f);
      bloom.setBloomIntesity(0.6f);
      bloom.setBlurPasses(4);
      Vignette vignette = new Vignette(Math.round(Gdx.graphics.getWidth() / 2f),
            Math.round(Gdx.graphics.getHeight() / 2f), false);
      vignette.setIntensity(0.45f);
      worldPipe.addEffects(vignette);
      worldPipe.addEffects(bloom);
   }
}