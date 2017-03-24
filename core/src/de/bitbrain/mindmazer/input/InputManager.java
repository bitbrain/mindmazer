package de.bitbrain.mindmazer.input;

import com.badlogic.gdx.InputAdapter;

import de.bitbrain.mindmazer.core.PreviewManager;

public class InputManager extends InputAdapter {

   private final PreviewManager previewManager;

   public InputManager(PreviewManager previewManager) {
      this.previewManager = previewManager;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      if (previewManager.isPreviewed()) {
         previewManager.obscure();
      }
      return super.touchDown(screenX, screenY, pointer, button);
   }
}
