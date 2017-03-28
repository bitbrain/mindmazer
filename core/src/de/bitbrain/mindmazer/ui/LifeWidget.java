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

   private final int COLAPSE_THRESHOLD = 5;

   private final Label label;
   private final GameStats stats;
   private final Sprite sprite;

   public LifeWidget(GameStats stats) {
      this.label = new HeightedLabel("0x", Colors.CELL_B_DARK, Styles.LABEL_TEXT_INFO);
      this.stats = stats;
      this.sprite = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.LIFE, Texture.class));
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
      label.setPosition(getX(), getY());
      label.draw(batch, parentAlpha);
   }

   private void drawFull(Batch batch, float parentAlpha) {
      final int padding = 5;
      final int visualHeight = 5;
      final int size = 64;
      sprite.setSize(size, size);
      for (int i = 0; i < stats.getLife(); ++i) {
         sprite.setColor(Colors.CELL_B_DARK);
         sprite.setPosition(getX() + i * (sprite.getWidth() + padding), getY() - visualHeight);
         sprite.draw(batch, parentAlpha);
         sprite.setColor(Colors.CELL_A);
         sprite.setPosition(getX() + i * (sprite.getWidth() + padding), getY());
         sprite.draw(batch, parentAlpha);
      }
   }
}
