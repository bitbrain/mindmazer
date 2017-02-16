package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.MindmazerGame;
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
      LevelStage levelStage = levelGenerator.generateLevel(3);
      GameObject world = getGameWorld().addObject();
      getLightingManager().setAmbientLight(new Color(1f, 1f, 1f, 1f));
      world.setActive(false);
      world.setType("world");
      getRenderManager().register("world", new LevelStageRenderer(levelStage.getCompleteData()));
   }
}
