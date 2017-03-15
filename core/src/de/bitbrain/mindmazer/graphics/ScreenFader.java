package de.bitbrain.mindmazer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import de.bitbrain.braingdx.graphics.pipeline.RenderLayer;
import de.bitbrain.braingdx.tweens.ColorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;

public class ScreenFader implements RenderLayer, Disposable {

   public static interface ScreenFadeCallback {
      void afterFade();
   }

   private SpriteBatch batch = new SpriteBatch();

   private Texture texture;

   private Color color = Color.WHITE.cpy();

   public ScreenFader() {
      color.a = 0f;
   }

   @Override
   public void beforeRender() {
      // TODO Auto-generated method stub

   }

   public void fadeIn() {
      fadeIn(1f);
   }

   public void fadeOut() {
      fadeOut(1f);
   }

   public void fadeOutAndIn(final ScreenFadeCallback complete, final float duration) {
      fadeOut(new ScreenFadeCallback() {
         @Override
         public void afterFade() {
            fadeIn(complete, duration / 2f);
         }
      }, duration / 2f);
   }

   public void fadeIn(float duration) {
      fadeIn(null, duration);
   }

   public void fadeOut(float duration) {
      fadeOut(null, duration);
   }

   public void fadeIn(final ScreenFadeCallback callback, float duration) {
      SharedTweenManager.getInstance().killTarget(color);
      color.a = 1f;
      Tween tween = Tween.to(color, ColorTween.A, duration)
           .target(0f)
           .ease(TweenEquations.easeInCubic);
      if (callback != null) {
      tween.setCallbackTriggers(TweenCallback.COMPLETE)
           .setCallback(new TweenCallback() {
               @Override
               public void onEvent(int arg0, BaseTween<?> arg1) {
                  callback.afterFade();
               }              
           });
      };
      tween.start(SharedTweenManager.getInstance());
   }

   public void fadeOut(final ScreenFadeCallback callback, float duration) {
      SharedTweenManager.getInstance().killTarget(color);
      Tween tween = Tween.to(color, ColorTween.A, duration)
            .target(1f)
            .ease(TweenEquations.easeInCubic);
       if (callback != null) {
       tween.setCallbackTriggers(TweenCallback.COMPLETE)
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int arg0, BaseTween<?> arg1) {
                   callback.afterFade();
                }              
            });
       };
       tween.start(SharedTweenManager.getInstance());
   }

   @Override
   public void render(Batch arg0, float arg1) {
      if (texture == null) {
         initTexture();
      }
      batch.setColor(color);
      batch.begin();
      batch.draw(texture, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      batch.end();
   }

   @Override
   public void dispose() {
      if (texture != null) {
         texture.dispose();
      }
   }

   private void initTexture() {
      Pixmap map = new Pixmap(2, 2, Format.RGBA8888);
      map.setColor(Color.BLACK);
      map.fill();
      texture = new Texture(map);
      map.dispose();
   }

}
