package de.bitbrain.mindmazer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.bitbrain.braingdx.graphics.GameObjectRenderManager.GameObjectRenderer;
import de.bitbrain.braingdx.world.GameObject;

public class ShadowedRenderer implements GameObjectRenderer {

   private static final float PADDING = 5;

   private final GameObjectRenderer renderer;

   private Sprite sprite;

   public ShadowedRenderer(GameObjectRenderer renderer) {
      this.renderer = renderer;
   }

   @Override
   public void init() {
      renderer.init();
   }

   @Override
   public void render(GameObject object, Batch batch, float delta) {
      if (sprite == null) {
         int width = (int) (object.getWidth() - PADDING * 2);
         int height = (int) (object.getHeight() / 2.5f - PADDING / 2);
         Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
         pixmap.setColor(new Color(0f, 0f, 0f, 0.22f));
         pixmap.fillRectangle(0, 0, width, height);
         Texture texture = new Texture(pixmap);
         pixmap.dispose();
         sprite = new Sprite(texture);
      }
      
      sprite.setPosition(object.getLeft() + object.getOffset().x + PADDING,
            object.getTop() + object.getOffset().y + PADDING / 4);
      sprite.draw(batch);
      renderer.render(object, batch, delta);
   }

}