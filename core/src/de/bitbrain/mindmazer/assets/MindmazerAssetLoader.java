package de.bitbrain.mindmazer.assets;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import de.bitbrain.braingdx.assets.GameAssetLoader;

public class MindmazerAssetLoader implements GameAssetLoader {

   @Override
   public void put(Map<String, Class<?>> map) {
      map.put(Assets.Textures.PLAYER, Texture.class);
      map.put(Assets.ParticleEffects.JUMP_LAND, ParticleEffect.class);
      map.put(Assets.Fonts.RESOURCE, FreeTypeFontGenerator.class);
   }

}
