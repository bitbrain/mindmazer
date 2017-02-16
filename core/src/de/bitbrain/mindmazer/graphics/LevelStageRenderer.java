package de.bitbrain.mindmazer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import de.bitbrain.braingdx.graphics.GameObjectRenderManager.GameObjectRenderer;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;

public class LevelStageRenderer implements GameObjectRenderer {

   private byte[][] data;

   private Texture texture;

   public LevelStageRenderer(byte[][] data) {
      this.data = data;
   }

   @Override
   public void init() {
   }

   public void setStage(byte[][] data) {
      this.data = data;
   }

   @Override
   public void render(GameObject object, Batch batch, float delta) {
      if (texture == null) {
         buildTextureBuffer(batch);
      }
      batch.draw(texture, object.getLeft(), object.getHeight());
   }

   private void buildTextureBuffer(Batch batch) {
      int textureWidth = Config.TILE_SIZE * data.length;
      int textureHeight = Config.TILE_SIZE * data[0].length;
      if (texture != null) {
         texture.dispose();
      }
      Pixmap map = new Pixmap(textureWidth, textureHeight, Format.RGBA8888);
      map.setColor(new Color(0f, 0f, 0f, 0f));
      for (int indexX = 0; indexX < data.length; ++indexX) {
         for (int indexY = 0; indexY < data[indexX].length; ++indexY) {
            float x = Config.TILE_SIZE * indexX;
            float y = Config.TILE_SIZE * indexY;
            byte value = data[indexX][indexY];
            if (value != 0) {
               if (indexX % 2 == 0) {
                  if (indexY % 2 == 0) {
                     map.setColor(Colors.CELL_B);
                  } else {
                     map.setColor(Colors.CELL_A);
                  }
               } else {
                  if (indexY % 2 == 0) {
                     map.setColor(Colors.CELL_A);
                  } else {
                     map.setColor(Colors.CELL_B);
                  }
               }
               map.fillRectangle((int) x, (int) y, Config.TILE_SIZE, Config.TILE_SIZE);
            }
         }
      }
      texture = new Texture(map);
      map.dispose();
   }

}
