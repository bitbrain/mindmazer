package de.bitbrain.mindmazer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.bitbrain.mindmazer.MindmazerGame;

public class DesktopLauncher {
   public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.title = "mindmazer";
      config.width = 600;
      config.height = 850;
      config.vSyncEnabled = true;
      config.resizable = false;
      config.useHDPI = true;
      config.samples = 8;
		new LwjglApplication(new MindmazerGame(), config);
	}
}
