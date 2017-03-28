package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;

public class LifeWidget extends Widget {

   private static final int COLAPSE_THRESHOLD = 3;
   private static final int PADDING = 15;
   private static final int VISUAL_HEIGHT = 7;
   private static final int SIZE = 60;

   private final Label label;
   private final GameStats stats;
   private final Sprite sprite;
   private final Sprite background;

   public LifeWidget(GameStats stats) {
      this.label = new HeightedLabel("0x", Colors.CELL_B_DARK, Styles.LABEL_TEXT_INFO);
      this.label.setColor(Colors.CELL_A_DARK);
      this.stats = stats;
      this.sprite = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.LIFE, Texture.class));
      this.background = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.CHIME, Texture.class));
   }

   @Override
   public float getHeight() {
      return SIZE;
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      if (stats.getLife() >= COLAPSE_THRESHOLD) {
         drawCollapsed(batch, parentAlpha);
      } else {
         drawFull(batch, parentAlpha);
      }
   }

   private void drawCollapsed(Batch batch, float parentAlpha) {
      label.setText(stats.getLife() + "x");
      label.setPosition(getX() - label.getPrefWidth() - SIZE / 1.5f - 8f, getY() - VISUAL_HEIGHT - 10f);
      label.draw(batch, parentAlpha * getColor().a);
      sprite.setSize(SIZE / 1.3f, SIZE / 1.3f);
      sprite.setColor(Colors.CELL_B_DARK);
      sprite.setPosition(getX() - SIZE / 1.3f, getY() - VISUAL_HEIGHT);
      sprite.draw(batch, parentAlpha * getColor().a);
      sprite.setColor(Colors.CELL_A_DARK);
      sprite.setPosition(getX() - SIZE / 1.3f, getY());
      sprite.draw(batch, parentAlpha * getColor().a);
   }

   private void drawFull(Batch batch, float parentAlpha) {
      float scale = 2.5f;
      float totalWidth = stats.getLife() * SIZE + stats.getLife() * PADDING - 1;
      background.setSize(scale * totalWidth, SIZE * scale);
      background.setPosition(getX() - (background.getWidth() - totalWidth),
            getY() - (background.getHeight() / 2f - SIZE / 2f));
      background.setColor(Colors.CELL_B_DARK);
      background.setAlpha(0.15f * getColor().a);
      background.draw(batch);
      sprite.setSize(SIZE, SIZE);
      for (int i = 0; i < stats.getLife(); ++i) {
         sprite.setColor(Colors.CELL_B_DARK);
         sprite.setPosition(getX() - i * (sprite.getWidth() + PADDING) - SIZE, getY() - VISUAL_HEIGHT);
         sprite.draw(batch, parentAlpha * getColor().a);
         sprite.setColor(Colors.CELL_A_DARK);
         sprite.setPosition(getX() - i * (sprite.getWidth() + PADDING) - SIZE, getY());
         sprite.draw(batch, parentAlpha * getColor().a);
      }
   }
}
