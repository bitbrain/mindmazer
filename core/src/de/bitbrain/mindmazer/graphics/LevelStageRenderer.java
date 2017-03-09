package de.bitbrain.mindmazer.graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager.GameObjectRenderer;
import de.bitbrain.braingdx.tweens.ColorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.SpriteTween;
import de.bitbrain.braingdx.tweens.VectorTween;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.Config;

public class LevelStageRenderer implements GameObjectRenderer {

   private static final int CELL_OFFSET = (int) (Config.TILE_SIZE * 0.23f);
   private static final float CELL_ANIMATION_TIME = 0.45f;

   private byte[][] data;

   private Texture texture;

   private Texture blockA, blockB;

   private Sprite sprite;

   private boolean renderRequest = true;

   private Set<String> cellIds = new HashSet<String>();

   private List<Cell> cells = new ArrayList<Cell>();

   private TweenManager tweenManager = SharedTweenManager.getInstance();

   public LevelStageRenderer(byte[][] data) {
      this.data = data;
   }

   @Override
   public void init() {
      Pixmap map = new Pixmap(Config.TILE_SIZE, Config.TILE_SIZE + CELL_OFFSET, Format.RGBA8888);
      map.setColor(Colors.CELL_A_DARK);
      map.fillRectangle(0, CELL_OFFSET, Config.TILE_SIZE, Config.TILE_SIZE);
      map.setColor(Colors.CELL_A);
      map.fillRectangle(0, 0, Config.TILE_SIZE, Config.TILE_SIZE);
      blockA = new Texture(map);
      map.setColor(Colors.CELL_B_DARK);
      map.fillRectangle(0, CELL_OFFSET, Config.TILE_SIZE, Config.TILE_SIZE);
      map.setColor(Colors.CELL_B);
      map.fillRectangle(0, 0, Config.TILE_SIZE, Config.TILE_SIZE);
      blockB = new Texture(map);
      map.dispose();
   }

   public void addCell(int x, int y) {
      if (!isCurrentlyProcessed(x, y)) {
         Cell cell = new Cell(x, y);
         cellIds.add(cell.getId());
         cells.add(cell);
         animate(cell);
      }
   }

   public void reset() {
      renderRequest = true;
      cells.clear();
      cellIds.clear();
   }

   public void setStage(byte[][] data) {
      this.data = data;
      if (texture != null) {
         texture.dispose();
         reset();
      }
   }

   @Override
   public void render(GameObject object, Batch batch, float delta) {
      if (renderRequest) {
         buildTextureBuffer(batch);
         renderRequest = false;
      }
      for (int i = cells.size() - 1; i >= 0; i--) {
         cells.get(i).draw(batch);
      }
      sprite.setFlip(false, true);
      sprite.setPosition(object.getLeft(), object.getTop() - CELL_OFFSET * 2);
      sprite.draw(batch);
   }

   private void animate(final Cell cell) {
      cell.getSprite().setScale(0.5f);
      Tween.to(cell.getColor(), ColorTween.A, CELL_ANIMATION_TIME)
           .target(1f)
           .ease(TweenEquations.easeInCubic)
           .start(tweenManager);
      Tween.to(cell.getSprite(), SpriteTween.SCALE, CELL_ANIMATION_TIME * 1.5f)
           .target(1f)
           .ease(TweenEquations.easeInCubic)
           .setCallbackTriggers(TweenCallback.COMPLETE)
           .setCallback(new TweenCallback() {
            @Override
            public void onEvent(int arg0, BaseTween<?> arg1) {
               cellIds.remove(cell.getId());
               cells.remove(cell);
               renderRequest = true;
            }              
           })
           .start(tweenManager);
      cell.getOffset().y = -CELL_OFFSET * 4f;
      Tween.to(cell.getOffset(), VectorTween.POS_Y, CELL_ANIMATION_TIME)
      .target(0f)
      .ease(TweenEquations.easeInCubic)
      .start(tweenManager);
   }

   private void buildTextureBuffer(Batch batch) {

      int textureWidth = Config.TILE_SIZE * data.length;
      int textureHeight = Config.TILE_SIZE * data[0].length + CELL_OFFSET;
      if (texture != null) {
         texture.dispose();
      }
      Pixmap map = new Pixmap(textureWidth, textureHeight, Format.RGBA8888);
      map.setColor(new Color(0f, 0f, 0f, 0f));
      for (int indexX = 0; indexX < data.length; ++indexX) {
         for (int indexY = data[indexX].length - 1; indexY >= 0; --indexY) {
            if (isCurrentlyProcessed(indexX, indexY)) {
               continue;
            }
            float x = Config.TILE_SIZE * indexX;
            float y = Config.TILE_SIZE * indexY;
            byte value = data[indexX][indexY];
            if (value != 0) {
               if (indexX % 2 == 0) {
                  if (indexY % 2 == 0) {
                     map.setColor(Colors.CELL_B_DARK);
                  } else {
                     map.setColor(Colors.CELL_A_DARK);
                  }
               } else {
                  if (indexY % 2 == 0) {
                     map.setColor(Colors.CELL_A_DARK);
                  } else {
                     map.setColor(Colors.CELL_B_DARK);
                  }
               }
               map.fillRectangle((int) x, (int) y, Config.TILE_SIZE, Config.TILE_SIZE);
            }
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
               map.fillRectangle((int) x, (int) y + CELL_OFFSET, Config.TILE_SIZE, Config.TILE_SIZE);
            }
         }
      }
      texture = new Texture(map);
      sprite = new Sprite(texture);
   }

   private boolean isCurrentlyProcessed(int x, int y) {
      return cellIds.contains(convertToId(x, y));
   }

   private String convertToId(int x, int y) {
      return x + "_" + y;
   }

   private Texture resolveBlockTexture(int x, int y) {
      if (x % 2 == 0) {
         if (y % 2 == 0) {
            return blockB;
         } else {
            return blockA;
         }
      } else {
         if (y % 2 == 0) {
            return blockA;
         } else {
            return blockB;
         }
      }
   }

   private class Cell {

      private final int x, y;

      private final String id;

      private final Color color;

      private final Sprite sprite;

      private final Vector2 offset;

      Cell(int x, int y) {
         this.x = x;
         this.y = y;
         this.id = convertToId(x, y);
         this.color = new Color(1f, 1f, 1f, 0f);
         sprite = new Sprite(resolveBlockTexture(x, y));
         offset = new Vector2(0, 0);

      }

      void draw(Batch batch) {
         float xPos = Config.TILE_SIZE * x;
         float yPos = Config.TILE_SIZE * y - CELL_OFFSET * 2;
         sprite.setPosition(xPos + offset.x, yPos + offset.y);
         sprite.setAlpha(color.a);
         sprite.draw(batch);
      }

      Vector2 getOffset() {
         return offset;
      }

      Sprite getSprite() {
         return sprite;
      }

      Color getColor() {
         return color;
      }

      String getId() {
         return id;
      }
   }

}
