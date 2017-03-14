package de.bitbrain.mindmazer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;

import de.bitbrain.braingdx.assets.SharedAssetManager;

public class BitmapFontBaker {

   public static BitmapFont bake(String fontPath, int fontSize) {
      FreeTypeFontGenerator generator = SharedAssetManager.getInstance().get(fontPath, FreeTypeFontGenerator.class);
      FreeTypeFontParameter parameter = new FreeTypeFontParameter();
      parameter.size = fontSize;
      parameter.kerning = true;
      parameter.hinting = Hinting.None;
      parameter.minFilter = Texture.TextureFilter.Nearest;
      parameter.magFilter = Texture.TextureFilter.Nearest;
      parameter.mono = true;
      parameter.color = Color.WHITE.cpy();
      BitmapFont font = generator.generateFont(parameter);
      generator.dispose();
      return font;
   }
}
