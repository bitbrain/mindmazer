package de.bitbrain.mindmazer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

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
		new LwjglApplication(new MindmazerGame(new SocialManager() {

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
        }), config);
	}
}
