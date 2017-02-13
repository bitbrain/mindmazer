package de.bitbrain.mindmazer;

import de.bitbrain.braingdx.BrainGdxGame;
import de.bitbrain.braingdx.assets.GameAssetLoader;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.mindmazer.assets.MindmazerAssetLoader;
import de.bitbrain.mindmazer.screens.IngameScreen;

public class MindmazerGame extends BrainGdxGame {

   @Override
   protected GameAssetLoader getAssetLoader() {
      return new MindmazerAssetLoader();
   }

   @Override
   protected AbstractScreen<?> getInitialScreen() {
      return new IngameScreen(this);
   }
}
