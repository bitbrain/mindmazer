package de.bitbrain.mindmazer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

import de.bitbrain.braingdx.BrainGdxGame;
import de.bitbrain.braingdx.assets.GameAssetLoader;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.audio.AudioManager;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.mindmazer.assets.MindmazerAssetLoader;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.screens.IngameScreen;
import de.bitbrain.mindmazer.screens.IntroScreen;
import de.bitbrain.mindmazer.screens.MenuScreen;
import de.bitbrain.mindmazer.social.SocialManager;
import de.bitbrain.mindmazer.ui.Styles;

public class MindmazerGame extends BrainGdxGame {

   private final SocialManager socialManager;

   public MindmazerGame(SocialManager socialManager) {
      this.socialManager = socialManager;
   }

   @Override
   public void resize(int width, int height) {
      super.resize(width, height);
   }

   @Override
   protected GameAssetLoader getAssetLoader() {
      SharedAssetManager.getInstance().setLoader(FreeTypeFontGenerator.class,
            new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
      return new MindmazerAssetLoader();
   }

   @Override
   protected AbstractScreen<?> getInitialScreen() {
   	if (Config.DEBUG) {
   		Gdx.app.setLogLevel(Application.LOG_DEBUG);
   	}
      Styles.init();
      Bundle.load();
      AudioManager.getInstance().setVolume(Config.MUSIC_VOLUME);
      if (Config.DEBUG) {
         return new IngameScreen(this);
      } else {
         return new IntroScreen(this);
      }
   }
}
