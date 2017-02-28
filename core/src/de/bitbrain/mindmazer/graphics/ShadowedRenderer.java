package de.bitbrain.mindmazer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager.GameObjectRenderer;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.VectorTween;
import de.bitbrain.braingdx.world.GameObject;

public class ShadowedRenderer implements GameObjectRenderer, RasteredMovementListener {

   private static final float PADDING = 5;

   private static final float JUMP_HEIGHT = 11f;

   private final SpriteRenderer renderer;

   private Sprite sprite;

   public ShadowedRenderer(SpriteRenderer renderer) {
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
      float additionalScale = -0.1f + renderer.getOffset().y / JUMP_HEIGHT;
      sprite.setScale(object.getScale().x - additionalScale, object.getScale().y - additionalScale);
      sprite.setPosition(object.getLeft() + object.getOffset().x + PADDING,
            object.getTop() + object.getOffset().y + PADDING / 4);
      sprite.draw(batch);
      sprite.setAlpha(object.getColor().a + additionalScale);
      renderer.render(object, batch, delta);
   }

   @Override
   public void moveBefore(GameObject object, float moveX, float moveY, float interval) {
      TweenManager tweenManager = SharedTweenManager.getInstance();
      tweenManager.killTarget(renderer.getOffset());
      Tween.to(renderer.getOffset(), VectorTween.POS_Y, interval / 2f)
            .target(JUMP_HEIGHT)
            .ease(TweenEquations.easeOutCubic)
            .repeatYoyo(1, 0f).start(tweenManager);
   }

   @Override
   public void moveAfter(GameObject arg0) {
      // noOp
   }

}
