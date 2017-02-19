package de.bitbrain.mindmazer.assets;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

import de.bitbrain.braingdx.assets.GameAssetLoader;

public class MindmazerAssetLoader implements GameAssetLoader {

   @Override
   public void put(Map<String, Class<?>> map) {
      map.put(Assets.Textures.PLAYER, Texture.class);
   }

}
