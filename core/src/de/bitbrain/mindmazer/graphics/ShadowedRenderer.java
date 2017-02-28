package de.bitbrain.mindmazer.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.behavior.movement.RasteredMovementBehavior.RasteredMovementListener;
import de.bitbrain.braingdx.graphics.GameObjectRenderManager.GameObjectRenderer;
import de.bitbrain.braingdx.graphics.particles.ManagedParticleEffect;
import de.bitbrain.braingdx.graphics.particles.ParticleManager;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.VectorTween;
import de.bitbrain.braingdx.world.GameObject;

public class ShadowedRenderer implements GameObjectRenderer, RasteredMovementListener {

   private static final float PADDING = 5;

   private static final float JUMP_HEIGHT = 11f;

   private final SpriteRenderer renderer;
   private final ParticleManager particleManager;

   private Sprite sprite;

   private final List<ManagedParticleEffect> effects = new ArrayList<ManagedParticleEffect>();
   private final List<ManagedParticleEffect> deletions = new ArrayList<ManagedParticleEffect>();
   private final Map<ManagedParticleEffect, Vector2> positions = new HashMap<ManagedParticleEffect, Vector2>();

   public ShadowedRenderer(SpriteRenderer renderer, ParticleManager particleManager) {
      this.renderer = renderer;
      this.particleManager = particleManager;
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
      sprite.setAlpha(object.getColor().a + additionalScale);
      sprite.draw(batch);
      for (ManagedParticleEffect effect : effects) {
         Vector2 pos = positions.get(effect);
         effect.render(pos.x, pos.y, batch, delta);
         if (particleManager.free(effect, false)) {
            deletions.add(effect);
         }
      }
      renderer.render(object, batch, delta);
      for (ManagedParticleEffect effect : deletions) {
         effects.remove(effect);
         positions.remove(effect);
      }
      deletions.clear();
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
   public void moveAfter(GameObject object) {
      /*
       * FIXME make particle effects more consistent ManagedParticleEffect effect =
       * particleManager.create(Assets.ParticleEffects.JUMP_LAND); effects.add(effect);
       * positions.put(effect, new Vector2(object.getLeft() + object.getWidth() / 2f,
       * object.getTop() + object.getHeight() / 5f));
       */
   }

}
