package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.core.GameStats;

public class LifeLabel extends HeightedLabel {

   private final GameStats stats;
   private final Sprite sprite;

   public LifeLabel(GameStats stats, HeightedLabelStyle style) {
   	super("0 Life", style);
      this.stats = stats;
      this.sprite = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.LIFE, Texture.class));
   }
   
   @Override
   public void act(float delta) {
   	setText(stats.getLife() + "x");
      super.act(delta);
      sprite.setSize(getPrefHeight() * 0.7f, getPrefHeight() * 0.7f);
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
      final float offset = 5f;
      sprite.setColor(Color.GRAY);
      sprite.setPosition(getX() + getPrefWidth(), getY() - 5f + offset);
      sprite.draw(batch, parentAlpha);
      sprite.setColor(Color.WHITE);
      sprite.setPosition(getX() + getPrefWidth(), getY() + offset);
      sprite.draw(batch, parentAlpha);
   }
}
