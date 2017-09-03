package de.bitbrain.mindmazer.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.ImageIcon;

import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.social.SocialManager;

public class DesktopLauncher {
   public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.title = Config.GAME_NAME + " " + Config.GAME_VERSION;
      config.width = 600;
      config.height = 850;
      config.vSyncEnabled = true;
      config.resizable = false;
      config.useHDPI = true;
      config.samples = 8;
      setApplicationIcon(config);
		new LwjglApplication(new MindmazerGame(emptySocialManager), config);
	}

   private static void setApplicationIcon(LwjglApplicationConfiguration config) {
      try {
         config.addIcon("icon-16.png", Files.FileType.Internal);
         config.addIcon("icon-32.png", Files.FileType.Internal);
         config.addIcon("icon-128.png", Files.FileType.Internal);
         Class<?> cls = Class.forName("com.apple.eawt.Application");
         Object application = cls.newInstance().getClass().getMethod("getApplication").invoke(null);
         FileHandle icon = Gdx.files.internal("icon-128.png");
         application.getClass().getMethod("setDockIconImage", java.awt.Image.class)
                 .invoke(application, new ImageIcon(icon.file().getAbsolutePath()).getImage());
      } catch (Exception e) {
         // nobody cares!
      }
   }

   private static SocialManager emptySocialManager = new SocialManager() {

      @Override
      public void login() {

      }

      @Override
      public void logout() {

      }

      @Override
      public boolean isSignedIn() {
         return false;
      }

      @Override
      public void showLadder() {

      }

      @Override
      public void showAchievements() {

      }

      @Override
      public boolean isConnected() {
         return false;
      }
   };
}
