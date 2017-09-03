package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior;
import de.bitbrain.braingdx.graphics.lighting.PointLightBehavior;
import de.bitbrain.braingdx.graphics.pipeline.RenderPipe;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.OrientationMovementController;
import de.bitbrain.braingdx.postprocessing.effects.Bloom;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.Types;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.assets.Assets.Textures;
import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.core.LevelManager;
import de.bitbrain.mindmazer.core.PreviewManager;
import de.bitbrain.mindmazer.core.handlers.GameOverHandler;
import de.bitbrain.mindmazer.core.handlers.GameStatsHandler;
import de.bitbrain.mindmazer.core.handlers.LevelLoaderHandler;
import de.bitbrain.mindmazer.graphics.CellRenderHandler;
import de.bitbrain.mindmazer.graphics.JumpAnimationRenderer;
import de.bitbrain.mindmazer.input.InputManager;
import de.bitbrain.mindmazer.levelgen.LevelGenerator;
import de.bitbrain.mindmazer.preferences.PrefKeys;
import de.bitbrain.mindmazer.ui.CurrentStageLabel;
import de.bitbrain.mindmazer.ui.LifeLabel;
import de.bitbrain.mindmazer.ui.PopupMenu;
import de.bitbrain.mindmazer.ui.Styles;
import de.bitbrain.mindmazer.ui.Toast;
import de.bitbrain.mindmazer.util.LogTags;
import de.bitbrain.mindmazer.util.StringUtils;

public class IngameScreen extends AbstractScreen<MindmazerGame> {

   private RasteredMovementBehavior behavior;
   private LevelManager levelManager;
   private GameStats stats;
   private PreviewManager previewManager;
   private GameContext context;

   public IngameScreen(MindmazerGame game) {
      super(game);
   }

   @Override
   protected void onCreate(GameContext context) {
      Gdx.app.log(LogTags.INIT, "Initialising ingame screen...");
      Toast.getInstance().setStage(context.getStage());
      this.context = context;
      setBackgroundColor(Colors.BACKGROUND);
      GameObject world = setupWorld();
      stats = new GameStats();
      LevelGenerator levelGenerator = new LevelGenerator(stats);
      levelManager = new LevelManager(context.getRenderManager(), world, levelGenerator);
      Preferences prefs = Gdx.app.getPreferences(Config.PREFERENCE_ID);
      levelManager.generateLevelStage(prefs.getString(PrefKeys.LEVEL_SEED, StringUtils.generateRandomString(Config.SEED_STRING_LENGTH)));
      GameObject player = setupNewPlayer(levelManager);
      previewManager = new PreviewManager(levelManager, player, world, context.getGameCamera());
      InputManager inputManager = new InputManager(previewManager, behavior);
      context.getInput().addProcessor(inputManager);
      setupUI(context.getStage(), stats);
      setupCamera(player);
      setupShaders();
      setupRenderers();
      setupGameHandlers(player, levelManager, behavior, previewManager);
      context.getScreenTransitions().in(1.5f);
      previewManager.preview();
      context. getAudioManager().playMusic(Assets.Musics.INGAME_01);
      Gdx.app.log(LogTags.INIT, "Initialised ingame screen.");
   }

   @Override
   public void dispose() {
      super.dispose();
      context.getAudioManager().stopMusic(Assets.Musics.INGAME_01);
   }

   @Override
   protected Viewport getViewport(int width, int height) {
      return new FitViewport(width, height);
   }

   @Override
   protected void onUpdate(float delta) {
      super.onUpdate(delta);
      stats.update(delta);
   }

   private GameObject setupWorld() {
      GameObject world = context.getGameWorld().addObject();
      context.getLightingManager().setAmbientLight(new Color(0.7f, 0.7f, 0.8f, 1f));
      world.setActive(false);
      world.setType(Types.WORLD);
      return world;
   }

   private void setupRenderers() {
      JumpAnimationRenderer jumpAnimationRenderer = new JumpAnimationRenderer(new SpriteRenderer(Textures.PLAYER));
      context.getRenderManager().register(Types.PLAYER, jumpAnimationRenderer);
      behavior.addListener(jumpAnimationRenderer);
   }

   private GameObject setupNewPlayer(LevelManager levelManager) {
      GameObject player = context.getGameWorld().addObject();
      player.setType(Types.PLAYER);
      player.setZIndex(10);
      player.setDimensions(Config.TILE_SIZE, Config.TILE_SIZE);
      player.setPosition(levelManager.getCurrentStage().getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
            levelManager.getCurrentStage().getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
      OrientationMovementController controller = new OrientationMovementController();
      behavior = new RasteredMovementBehavior(controller)
            .interval(0.4f).rasterSize(Config.TILE_SIZE, Config.TILE_SIZE);
      context.getBehaviorManager().apply(behavior, player);
      context.getBehaviorManager().apply(new PointLightBehavior(Color.WHITE, 200f, context.getLightingManager()),
            player);
      return player;
   }

   private void setupGameHandlers(GameObject player, LevelManager levelManager,
         RasteredMovementBehavior behavior, PreviewManager previewManager) {
      behavior.addListener(new GameStatsHandler(levelManager, stats));
      behavior.addListener(new GameOverHandler(levelManager, context.getGameCamera(), previewManager, getGame(), stats));
      behavior.addListener(new LevelLoaderHandler(levelManager, player, stats, previewManager));
      behavior.addListener(new CellRenderHandler(levelManager));
   }

   private void setupCamera(GameObject target) {
	  context.getGameCamera().setTarget(target);
	  context.getGameCamera().setSpeed(2.2f);
	  if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
		  context.getGameCamera().setBaseZoom(Config.BASE_ZOOM_DESKTOP);
	  } else {
		  context.getGameCamera().setBaseZoom(Config.BASE_ZOOM);
	  }
	  context.getGameCamera().setZoomScale(0.001f);
   }

   private void setupShaders() {
      if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
	      RenderPipe worldPipe = context.getRenderPipeline().getPipe(RenderPipeIds.WORLD);
	      Vignette vignette = new Vignette(Math.round(Gdx.graphics.getWidth() / 2f), Math.round(Gdx.graphics.getHeight() / 2f), false);
	      vignette.setIntensity(0.45f);
	      worldPipe.addEffects(vignette);
	      RenderPipe uiPipe = context.getRenderPipeline().getPipe(RenderPipeIds.UI);
		   Bloom bloom = new Bloom(Math.round(Gdx.graphics.getWidth() * 0.7f), Math.round(Gdx.graphics.getHeight() * 0.7f));
		   bloom.setBaseIntesity(0.8f);
		   bloom.setBaseSaturation(1.7f);
		   bloom.setBlurAmount(0.7f);
		   bloom.setBloomSaturation(0.8f);
	      bloom.setBloomIntesity(0.6f);
	      bloom.setBlurPasses(4);
	      uiPipe.addEffects(bloom);
      }
   }

   private void setupUI(Stage stage, GameStats stats) {
      final float padding = 20f;
      CurrentStageLabel progressLabel = new CurrentStageLabel(stats, Styles.LABEL_TEXT_INFO);
      LifeLabel lifeWidget = new LifeLabel(stats, Styles.LABEL_TEXT_INFO_PLASMA);
      lifeWidget.setPosition(Gdx.graphics.getWidth() - padding * 2f - lifeWidget.getPrefWidth(),
            Gdx.graphics.getHeight() - padding - lifeWidget.getPrefHeight());
      progressLabel.setPosition(padding * 2f, Gdx.graphics.getHeight() - padding - progressLabel.getHeight());
      stage.addActor(progressLabel);
      stage.addActor(lifeWidget);
      
      PopupMenu popupMenu = new PopupMenu();
      float buttonSize = 80f;
      popupMenu.setPosition(Gdx.graphics.getWidth() - padding * 2f - buttonSize, padding * 2f);
      popupMenu.setSize(buttonSize, buttonSize);
      stage.addActor(popupMenu);
      popupMenu.add(Assets.Textures.EXIT, "Exit game", new ClickListener() {
      	@Override
      	public void clicked(InputEvent event, float x, float y) {
      		Gdx.app.exit();
      	}
      });
      popupMenu.add(Assets.Textures.MUTE, "Toggle sound", new ClickListener() {
      	@Override
      	public void clicked(InputEvent event, float x, float y) {
      		// TODO
      	}
      });
      popupMenu.add(Assets.Textures.ACHIEVEMENTS, "Achievements", null);
   }
}
