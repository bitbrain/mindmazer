package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.input.OrientationMovementController;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets.Textures;
import de.bitbrain.mindmazer.graphics.LevelStageRenderer;
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
      getLightingManager().setAmbientLight(new Color(1f, 1f, 1f, 1f));
      world.setActive(false);
      world.setType("world");
      getRenderManager().register("world", new LevelStageRenderer(levelStage.getCompleteData()));

      GameObject player = getGameWorld().addObject();
      player.setType("player");
      player.setZIndex(10);
      player.setDimensions(Config.TILE_SIZE, Config.TILE_SIZE);
      player.setPosition(levelStage.getAbsoluteStartOffsetX(0) * Config.TILE_SIZE,
            levelStage.getAbsoluteStartOffsetY(0) * Config.TILE_SIZE);
      getRenderManager().register("player", new SpriteRenderer(Textures.PLAYER));
      getGameCamera().setTarget(player);
      getGameCamera().setBaseZoom(0.2f);
      getGameCamera().setZoomScale(0.001f);

      OrientationMovementController controller = new OrientationMovementController();
      RasteredMovementBehavior behavior = new RasteredMovementBehavior(controller, levelStage)
            .interval(0.3f).rasterSize(Config.TILE_SIZE, Config.TILE_SIZE);
      getBehaviorManager().apply(behavior, player);

   }
}
