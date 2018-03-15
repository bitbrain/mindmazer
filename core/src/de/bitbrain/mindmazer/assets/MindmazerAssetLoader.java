package de.bitbrain.mindmazer.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Map;

import de.bitbrain.braingdx.assets.GameAssetLoader;

public class MindmazerAssetLoader implements GameAssetLoader {

   @Override
   public void put(Map<String, Class<?>> map) {
      textures(map);
      particles(map);
      audio(map);
      fonts(map);
   }

   private void textures(Map<String, Class<?>> map) {
      map.put(Assets.Textures.PLAYER, Texture.class);
      map.put(Assets.Textures.BITBRAIN_LOGO, Texture.class);
      map.put(Assets.Textures.LIFE, Texture.class);
      map.put(Assets.Textures.CHIME, Texture.class);
      map.put(Assets.Textures.BUTTON_9, Texture.class);
      map.put(Assets.Textures.MENU, Texture.class);
      map.put(Assets.Textures.MUTE, Texture.class);
      map.put(Assets.Textures.ACHIEVEMENTS, Texture.class);
      map.put(Assets.Textures.EXIT, Texture.class);
   }

   private void particles(Map<String, Class<?>> map) {
      map.put(Assets.ParticleEffects.JUMP_LAND, ParticleEffect.class);
   }

   private void audio(Map<String, Class<?>> map) {
      map.put(Assets.Musics.MAINMENU, Music.class);
      map.put(Assets.Musics.INGAME_01, Music.class);
      map.put(Assets.Musics.INGAME_02, Music.class);
      map.put(Assets.Musics.INGAME_03, Music.class);
      map.put(Assets.Musics.GAMEOVER, Music.class);

      map.put(Assets.Sounds.BITBRAIN, Sound.class);
      map.put(Assets.Sounds.BUTTON_CLICK, Sound.class);
      map.put(Assets.Sounds.DEATH, Sound.class);
      map.put(Assets.Sounds.GAME_OVER, Sound.class);
      map.put(Assets.Sounds.JUMP_01, Sound.class);
      map.put(Assets.Sounds.LAND_01, Sound.class);
      map.put(Assets.Sounds.LIFE_UP, Sound.class);
      map.put(Assets.Sounds.OBSCURE_MAP, Sound.class);
      map.put(Assets.Sounds.SHOW_MAP, Sound.class);
      map.put(Assets.Sounds.LEVEL_COMPLETE, Sound.class);
   }

   private void fonts(Map<String, Class<?>> map) {
      map.put(Assets.Fonts.ANGIES_NEW_HOUSE, FreeTypeFontGenerator.class);
   }

}
