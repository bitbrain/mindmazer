package de.bitbrain.mindmazer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import de.bitbrain.braingdx.behavior.movement.Movement;
import de.bitbrain.braingdx.behavior.movement.Orientation;
import de.bitbrain.mindmazer.Config;
import de.bitbrain.mindmazer.core.PreviewManager;

public class InputManager extends InputAdapter {

   private final PreviewManager previewManager;
   private final Movement<Orientation> movement;

   public InputManager(PreviewManager previewManager, Movement<Orientation> movement) {
      this.previewManager = previewManager;
      this.movement = movement;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      if (previewManager.isPreviewed()) {
         previewManager.obscure();
      }
      return super.touchDown(screenX, screenY, pointer, button);
   }

   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer) {
      int deltaX = Gdx.input.getDeltaX(pointer);
      int deltaY = Gdx.input.getDeltaY(pointer);

      int absDeltaX = Math.abs(deltaX);
      int absDeltaY = Math.abs(deltaY);

      if (absDeltaX > Config.SWIPE_TOLERANCE && absDeltaX > absDeltaY) {
         if (deltaX < 0) {
            movement.move(Orientation.LEFT);
         } else {
            movement.move(Orientation.RIGHT);
         }
      } else if (absDeltaY > Config.SWIPE_TOLERANCE) {
         if (deltaY < 0) {
            movement.move(Orientation.UP);
         } else {
            movement.move(Orientation.DOWN);
         }
      } else {
         return false;
      }

      return true;
   }
}
