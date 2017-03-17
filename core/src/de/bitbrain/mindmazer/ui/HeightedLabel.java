package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.mindmazer.assets.Assets;

public class HeightedLabel extends Label {

   private final Sprite background;

   private final Color backgroundColor;

   public HeightedLabel(CharSequence text, Color backgroundColor, LabelStyle style) {
      super(text, style);
      this.backgroundColor = backgroundColor;
      background = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.CHIME, Texture.class));
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      background.setPosition(getX(), getY());
      background.setSize(getWidth(), getHeight());
      background.setColor(backgroundColor);
      background.setAlpha(0.2f);
      background.draw(batch);
      float x = getX();
      float y = getY();
      Color color = getColor().cpy();
      setPosition(x, y - 6f);
      setColor(backgroundColor);
      super.draw(batch, parentAlpha);
      setPosition(x, y);
      setColor(color);
      super.draw(batch, parentAlpha);
   }
}
