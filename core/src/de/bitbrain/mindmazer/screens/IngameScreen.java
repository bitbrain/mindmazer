package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import de.bitbrain.mindmazer.Types;
import de.bitbrain.mindmazer.assets.Assets.Textures;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.handlers.GameOverHandler;
import de.bitbrain.mindmazer.core.handlers.LevelLoaderHandler;
import de.bitbrain.mindmazer.graphics.CellRenderHandler;
import de.bitbrain.mindmazer.graphics.JumpAnimationRenderer;

public class IngameScreen extends AbstractScreen<MindmazerGame> {

   private RasteredMovementBehavior behavior;
   private LevelManager levelManager;

   public IngameScreen(MindmazerGame game) {
      super(game);
   }

   @Override
   protected void onCreateStage(Stage stage, int width, int height) {
      setBackgroundColor(Colors.BACKGROUND);
      levelManager = new LevelManager(getRenderManager());
      levelManager.generateLevelStage();
      GameObject player = setupNewPlayer(levelManager);
      setupWorld();
      setupCamera(player);
      setupShaders();
      setupRenderers();
   }

   @Override
   protected void onUpdate(float delta) {
      super.onUpdate(delta);
      if (Gdx.input.isKeyJustPressed(Keys.Q)) {
         levelManager.obscureLevel();
      } else if (Gdx.input.isKeyJustPressed(Keys.E)) {
         levelManager.revealLevel();

      }
   }

   private void setupWorld() {
      GameObject world = getGameWorld().addObject();
      getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      world.setActive(false);
      world.setType(Types.WORLD);
   }

   private void setupRenderers() {
      JumpAnimationRenderer jumpAnimationRenderer = new JumpAnimationRenderer(new SpriteRenderer(Textures.PLAYER));
      getRenderManager().register(Types.PLAYER, jumpAnimationRenderer);
      behavior.addListener(jumpAnimationRenderer);
   }

   private GameObject setupNewPlayer(LevelManager levelManager) {
      GameObject player = getGameWorld().addObject();
      player.setType(Types.PLAYER);
      player.setZIndex(10);
      player.setDimensions(Config.TILE_SIZE, Config.TILE_SIZE);
      player.setPosition(levelManager.getCurrentStage().getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
            levelManager.getCurrentStage().getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
      OrientationMovementController controller = new OrientationMovementController();
      behavior = new RasteredMovementBehavior(controller)
            .interval(0.4f).rasterSize(Config.TILE_SIZE, Config.TILE_SIZE);
      getBehaviorManager().apply(behavior, player);
      getBehaviorManager().apply(new PointLightBehavior(Color.WHITE, 200f, getLightingManager()),
            player);
      setupGameHandlers(player, levelManager, behavior);
      return player;
   }

   private void setupGameHandlers(GameObject player, LevelManager levelManager,
         RasteredMovementBehavior behavior) {
      behavior.addListener(new GameOverHandler(levelManager, getGameCamera()));
      behavior.addListener(new LevelLoaderHandler(levelManager, player));
      behavior.addListener(new CellRenderHandler(levelManager));
   }

   private void setupCamera(GameObject target) {
      getGameCamera().setTarget(target);
      getGameCamera().setSpeed(2.2f);
      getGameCamera().setBaseZoom(0.2f);
      getGameCamera().setZoomScale(0.001f);
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
